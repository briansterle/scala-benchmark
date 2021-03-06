package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._
import scalaz.{IList, ICons, INil}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class MatchContainersBench {

  var list: List[Int] = _
  var ilist: IList[Int] = _
  var vec: Vector[Int] = _
  var arr: Array[Int] = _
  var seq: Seq[Int] = _
  var stream: LazyList[Int] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000)
    ilist = IList.fromList(list)
    vec = Vector.range(1, 10000)
    arr = Array.range(1, 10000)
    seq = Seq.range(1, 10000)
    stream = LazyList.range(1, 10000)
  }

  @Benchmark
  def lastListMatchCons: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = l match {
      case Nil       => None
      case h :: Nil  => Some(h)
      case _ :: rest => work(rest)
    }

    work(list)
  }

  @Benchmark
  def lastListMatchGeneric: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = l match {
      case Nil       => None
      case h +: Nil  => Some(h)
      case _ +: rest => work(rest)
    }

    work(list)
  }

  /* Unlike Haskell, `tail` on an empty list throws an Exception */

  @Benchmark
  def lastListIf: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(list)
  }

  @Benchmark
  def lastIListMatchCons: Option[Int] = {
    @tailrec def work(l: IList[Int]): Option[Int] = l match {
      case INil()           => None
      case ICons(h, INil()) => Some(h)
      case ICons(_, rest)   => work(rest)
    }

    work(ilist)
  }

  @Benchmark
  def lastVectorMatchGeneric: Option[Int] = {
    @tailrec def work(l: Vector[Int]): Option[Int] = l match {
      case Vector() => None
      case h +: Vector() => Some(h)
      case _ +: rest => work(rest)
    }

    work(vec)
  }

  @Benchmark
  def lastVectorIf: Option[Int] = {
    @tailrec def work(l: Vector[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(vec)
  }

  @Benchmark
  def lastArrayIf: Option[Int] = {
    @tailrec def work(l: Array[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(arr)
  }

  @Benchmark
  def lastSeqMatchGeneric: Option[Int] = {
    @tailrec def work(l: Seq[Int]): Option[Int] = l match {
      case Seq() => None
      case h +: Seq() => Some(h)
      case _ +: rest => work(rest)
    }

    work(seq)
  }

  @Benchmark
  def lastSeqIf: Option[Int] = {
    @tailrec def work(l: Seq[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(seq)
  }

  @Benchmark
  def lastLazyListMatch: Option[Int] = {
    @tailrec def work(l: LazyList[Int]): Option[Int] = l match {
      case LazyList() => None
      case h #:: LazyList() => Some(h)
      case _ #:: rest => work(rest)
    }

    work(stream)
  }

  @Benchmark
  def lastLazyListMatchGeneric: Option[Int] = {
    @tailrec def work(l: LazyList[Int]): Option[Int] = l match {
      case LazyList() => None
      case h +: LazyList() => Some(h)
      case _ +: rest => work(rest)
    }

    work(stream)
  }

  @Benchmark
  def lastLazyListIf: Option[Int] = {
    @tailrec def work(l: LazyList[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(stream)
  }

}
