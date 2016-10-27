import scala.collection.immutable.Vector
import scala.util.Random
import breeze.stats.distributions.Gaussian
import breeze.stats.distributions.MultivariateGaussian
import breeze.stats.distributions.Multinomial
import breeze.linalg.DenseMatrix
import breeze.linalg.DenseVector
import breeze.linalg.{ Vector => breezeVector }
import breeze.stats.distributions.Dirichlet

object Main {
  def main(args: Array[String]) = {
    val numClasses = 3
    
//    val dirichlet = new Dirichlet(DenseVector(Vector.fill(numClasses)(10.0).toArray))
    val prior = Vector(.1,.3,.6)
//    val prior = Vector.fill(numClasses)(1.0/3)
    val alphas = Vector(Vector(.2,.2,.6),Vector(.1,.2,.3,.4),Vector(.5,.2,.3))
    val means = Vector(Vector(0.0,10,5),Vector(-10.0,-5,3,8),Vector(1.0,7,9))
    val stds = Vector(Vector(1.0,2.0,1.0),Vector(2.0,1.0,0.5,1.0),Vector(2.0,1.0,0.5))
    val mixtureOfGmms = new MixtureOfGMMs(prior,  alphas, means, stds)
 
    //sample from mixture
    val numBags = 10000
    val bagSize = 3
    val samples: Vector[(Int, Vector[Double])] = mixtureOfGmms.draw(numBags, bagSize)
    val actualPredicted: Vector[(Int,Int)] = samples.map(classData => (classData._1,mixtureOfGmms.classify(classData._2)))
    val accuracy: Double = actualPredicted.map(ap => if (ap._1 == ap._2) 1 else 0).sum.toDouble / actualPredicted.length
    println("Accuracy: " + accuracy)
    
  }
}

class GMM(alpha: Vector[Double], means: Vector[Double], std: Vector[Double]) {
  assert(alpha.length == means.length && alpha.length == std.length, "alpha vector must have same length as mean vector and std vector")
  val alphaMultinomial = new Multinomial(DenseVector(alpha.toArray))
  val numComponents = alpha.length
  val components: Vector[Gaussian] = means.zip(std).map(meanStd => new Gaussian(meanStd._1,meanStd._2))
  def f(x: Double): Double = {
    alpha.zip(components).map(alphaComponent => alphaComponent._1 * alphaComponent._2.pdf(x)).sum
  }
  def draw: Double = {
    //sample from alpha to get a component
    val compIndex = alphaMultinomial.draw
    components(compIndex).draw()
  }
  def logProbDataAndClass(data: Vector[Double], classProb: Double): Double = {
//    data.map(datum => Math.log(f(datum)*classProb)).sum
    data.map(datum => Math.log(f(datum))).sum + Math.log(classProb)
  }
}

class MixtureOfGMMs(prior: Vector[Double], alphas: Vector[Vector[Double]], means: Vector[Vector[Double]], stds: Vector[Vector[Double]]) {
  assert(prior.length == alphas.length && prior.length == means.length && prior.length == stds.length, "all parameter vectors must be of the same length")
  val priorMultinomial = new Multinomial(DenseVector(prior.toArray)) 
  val numGMMs = prior.length
  val GMMs: Vector[GMM] = (alphas, means, stds).zipped.toVector.map(params => new GMM(params._1, params._2, params._3))
  
  //returns vector of (class, bag of data)
  def draw(numBags: Int, bagSize: Int): Vector[(Int,Vector[Double])] = {
    //sample from prior
    val classSamples = 0.until(numBags).toVector.map(index => priorMultinomial.draw)
    //sample from gmm corresponding to above sample
    val samples: Vector[(Int,Vector[Double])] = classSamples.map(classIndex => (classIndex,Vector.fill(bagSize)(GMMs(classIndex).draw)))
    samples
  }
  
  def classify(bag: Vector[Double]): Int = {
    //for each GMM, calculate log likelihood of data under that distribution * prior and max over this
    GMMs.zip(prior).map(gmmPrior => gmmPrior._1.logProbDataAndClass(bag, gmmPrior._2)).zipWithIndex.maxBy(_._1)._2
  }
}



















