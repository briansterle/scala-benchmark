package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._
import scalaz.{IList, ICons, INil, EphemeralStream => EStream}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class FoldBench {

  var list: List[Int] = _
  var ilist: IList[Int] = _
  var vector: Vector[Int] = _
  var array: Array[Int] = _
  var stream: LazyList[Int] = _
  var estream: EStream[Int] = _

  @Setup
  def setup: Unit = {
    val size = 10000
    list = List.range(1, size)
    ilist = IList.fromList(list)
    vector = Vector.range(1, size)
    array = Array.range(1, size)
    stream = LazyList.range(1, size)
    estream = EStream.range(1, size)
  }

  @Benchmark
  def iterFoldLeft: Int = list.iterator.foldLeft(0)(_ + _)
  @Benchmark
  def iterFoldRight: Int = list.iterator.foldRight(0)(_ + _)
  @Benchmark
  def iterWhile: Int = {
    var n: Int = 0
    val iter: Iterator[Int] = list.iterator

    while (iter.hasNext) {
      n += iter.next
    }

    n
  }

  @Benchmark
  def listFoldLeft: Int = list.foldLeft(0)(_ + _)
  @Benchmark
  def listFoldRight: Int = list.foldRight(0)(_ + _)
  @Benchmark
  def listTailrec: Int = {
    @tailrec def work(l: List[Int], acc: Int): Int = l match {
      case h :: rest => work(rest, h + acc)
      case Nil => acc
    }

    work(list, 0)
  }
  @Benchmark
  def listWhile: Int = {
    var i: Int = 0
    var l: List[Int] = list

    while (!l.isEmpty) {
      i += l.head
      l = l.tail
    }

    i
  }
  @Benchmark
  def listSum: Int = list.sum

  @Benchmark
  def ilistFoldLeft: Int = ilist.foldLeft(0)(_ + _)
  @Benchmark
  def ilistFoldRight: Int = ilist.foldRight(0)(_ + _)
  @Benchmark
  def ilistTailrec: Int = {
    @tailrec def work(l: IList[Int], acc: Int): Int = l match {
      case ICons(h, tail) => work(tail, h + acc)
      case INil() => acc
    }

    work(ilist, 0)
  }

  @Benchmark
  def vectorFoldLeft: Int = vector.foldLeft(0)(_ + _)
  @Benchmark
  def vectorFoldRight: Int = vector.foldRight(0)(_ + _)
  @Benchmark
  def vectorWhile: Int = {
    var n: Int = 0
    var i: Int = vector.length - 1

    while (i >= 0) {
      n += vector(i)
      i -= 1
    }

    n
  }
  @Benchmark
  def vectorSum: Int = vector.sum

  @Benchmark
  def arrayFoldLeft: Int = array.foldLeft(0)(_ + _)
  @Benchmark
  def arrayFoldRight: Int = array.foldRight(0)(_ + _)
  @Benchmark
  def arrayWhile: Int = {
    var n: Int = 0
    var i: Int = array.length - 1

    while (i >= 0) {
      n += array(i)
      i -= 1
    }

    n
  }
  @Benchmark
  def arraySum: Int = array.sum

  @Benchmark
  def streamFoldLeft: Int = stream.foldLeft(0)(_ + _)
  @Benchmark
  def streamFoldRight: Int = stream.foldRight(0)(_ + _)
  @Benchmark
  def streamTailrec: Int = {
    @tailrec def work(l: LazyList[Int], acc: Int): Int = l match {
      case _ if l.isEmpty => acc
      case h #:: rest => work(rest, h + acc)
    }

    work(stream, 0)
  }
  @Benchmark
  def streamWhile: Int = {
    var i: Int = 0
    var l: LazyList[Int] = stream

    while (!l.isEmpty) {
      i += l.head
      l = l.tail
    }

    i
  }
  @Benchmark
  def streamSum: Int = stream.sum

  @Benchmark
  def estreamFoldLeft: Int = estream.foldLeft(0) { (n, acc) => n + acc }
  @Benchmark
  def estreamFoldRight: Int = estream.foldRight(0) { (n, acc) => n + acc }
}
