import java.lang.IndexOutOfBoundsException
import java.util.NoSuchElementException

//import scala.reflect.runtime.Settings.IntSetting

/*
def add(x:Int,y:Int)= x+y
var suma = (x:Int,y:Int)=> x+y

def add2(x:Int)=(y:Int)=> x+y
def add3(x:Int)(y:Int)= x+y


add(1,3)
suma(1,2)
add2(1)(2)


val sum = (a: Int, b: Int, c: Int) => a + b + c
val f = sum (1,2,_:Int)
sum
f _
f(3)

def sum2 (a: Int)(b: Int)(c: Int) = a + b + c
val f2 = sum2 (1)(2) _
f2
f2(4)

def sum3 (a: Int, b: Int, c: Int) = a + b + c
val sum_curried = (sum3 _).curried
sum_curried (1)(2)(3)


def sum4 (a: Int)(b: Int)(c: Int) = a + b + c
//val sum_uncurried = sum4.un  ??¿?

val even = (a:Int) => a % 2 == 0
even(1)
even(2)
def cuadrado (x:Int):Int = x*x			//devuelve una función
cuadrado(2)

val suma: (Int, Int)=> Int= _ + _
suma(2,1)


  def sum(f: Int => Int,a: Int, b: Int) = {
    def loop(a: Int, acc: Int): Int =
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
      loop(a, 0)
    }



def cubo (f:Int=>Int, y:Int):Int = {f(y)*y}
cubo (x=> x*x,2)



def product (f:Int=>Int)(a:Int, b:Int):Int ={
  if (a>b) 1
  else f(a) * product(f)(a+1,b)
}

product(x=> x*x)(3,4)

def fact (n:Int) = product(x=> x)(1,n)
fact(5)


def mapReduce (f:Int => Int, Combine:(Int,Int)=>Int, Zero:Int )(a:Int, b:Int):Int =
  if (a>b) 1
  else Combine (f(a),mapReduce(f,Combine,Zero)(a+1,b))

mapReduce(z=> z*z,(x,y)=> x*y,1)(3,4)

def mat (O:(Int, Int)=>Int)(x:Int, y:Int):Int ={
  O(x,y)
}

mat((x,y)=>x+y)(1,2)
mat((x,y)=>x*y)(1,2)
var O = (x:Int,y:Int)=>x+y
mat (O)(1,2)



val f = (x:Int) => x + 1
f.toString()
f(1)
f.apply(1)
f(1).toString



val f = (x:Int) => x + 1
val g = (x:Int) => x - 1

val f2 = f.compose((x:Int) => x - 1)
val f3 = f.compose(g)
f2(2)
f3(2)

val f4 = f.andThen(g)
f4(2)



case class f (name:String){println(name)}

val A = new f("Hi")
val B =  f("Bye")

println(A.name)
println(A==B)



trait  person  {
  protected  def delete {}
  def p_name (name:String){println(name)}
  def add_age (age:Int):Int
  }


class alumno extends  person {
  def add_age (age:Int):Int ={(age+1)}
  //def delete ={1}  //Fail
  override def  delete ={}
  }


val test = new alumno ()
test.p_name("My name")

*/

/*

implicit class StringImprovements(s: String)
  {def increment = s.map(c => c.toLower)}
 "HOLA".increment

*/

/*
object rationals{
  val x= new Rational (1,3)
  val y= new Rational (5,7)
  val z= new Rational (3,2)
  x.numer.toString()
  x.sub(y).sub(z).toString()
  x.toString()
  x.less(y)


}
class  Rational (x:Int,y:Int){
  require (y != 0, "denominator must be < > 0 ")
  private def gcd (a:Int,b:Int):Int = if (b==0) a else gcd (b,a%b)
  private val g = gcd(x,y)
  def numer:Int = x
  def denom :Int=  y

  def add (that: Rational) =
    new Rational(numer * that.denom + that.numer * denom,
      denom*that.denom)

  def neg() = new Rational(-numer, denom)
  def sub(that: Rational) =  add(that.neg)
  override def toString = numer.toString() + "/"+ denom   // Pq no me pinsta ¡¡¡
  def less (that: Rational) = numer * that.denom < that.numer *denom

}
*/

/*
abstract  class classAbstr {
  def sum: Int
}

trait myTrait {
  def printName (name:String){println(name)}
}

class myMinix extends classAbstr with myTrait{
  def sum: Int=0
}

val A = new myMinix()
A.sum
A.printName("Hi")
*/

/*
class class1
class class2
class class3

val A = List (new class1, new class2, new class3)
*/

/*
trait tr1 {def print1(name:String) = println(name.toUpperCase)}
trait tr2 {def print2(name:String) = println(name.toLowerCase)}

class C (name:String) extends tr1 {}

val X = new C ("Hi") with tr2
X.print1("Bye")
X.print2("Bye")
*/



/*
class number (x:Int) {
  def sum (y:Int):Int =(x+y)
  def sub (y:Int):Int =(x-y)
}
val X = new number(1).sub(1)
val Y = new number(2).sub(1)
val Z = new number(2) sub 1
*/

/*
var A = List (1,2,3)
def cuadrado (x:Int):Int = x*x
A.map(cuadrado)
List (1,2,3).map(cuadrado)
*/

/*
class entero (X:Int){
  def value:Int = X
  def + (Y:entero) = new entero (value+Y.value)
}

val uno = new entero(1)
val dos = new entero(2)
val B= uno + dos
B.value
*/

/*
type MyType = Int => Boolean

val positive1:MyType = (x:Int)=> x>0    // Use 1
positive1(1)

def positiveF (x:Int)= x>0              // Use 2
val positive3:MyType = positiveF
positive3(-1)
*/


/*
type Set = Int => Boolean

def contains(s: Set, elem: Int): Boolean = s(elem)
def singletonSet(elem: Int): Set = (x:Int) => x==elem
def union(s: Set, t: Set): Set = (x: Int) => s(x) || t(x)
def intersect(s: Set, t: Set): Set = (x: Int) => s(x) && t(x)
def diff(s: Set, t: Set): Set = (x: Int) => s(x) && !t(x)

trait TestSets {
  val s1 = singletonSet(1)
  val s2 = singletonSet(2)
  val s3 = singletonSet(3)
}


new TestSets {
  contains(s1, 1)
}

new TestSets {
  val s = union(s1, s2)
  }

*/

/*

abstract  class  IntSet {
  def incl (x:Int): IntSet
  def contains(x:Int): Boolean
  def union (other:IntSet):IntSet
}

class NonEmpty (elem:Int, left:IntSet, right:IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left.contains(x)
    else if (x > elem) right contains x
    else true

  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl (x), right)
    else if (x > elem) new NonEmpty(elem, left, right incl (x))
    else this

  override def toString = "{" + left + elem + right + "}"
  def union (other:IntSet):IntSet = ((left union right)union(other)) incl(elem)
}
class Empty extends  IntSet{
  def contains (x:Int):Boolean=false
  def incl(x:Int):IntSet=new NonEmpty(x,new Empty, new Empty)
  override def toString ="."
  def union (other:IntSet):IntSet = other
}

  object  xx {
    val t1 = new NonEmpty(3, new Empty, new Empty)
    val t2 = t1 incl 4
    t1.toString ()
    t2.toString ()

}
*/

/*
//val A:Int = null

if (true) 1 else false

*/

/*
def xx[A](a:A) =List(a)                 //OK
//def yy[A,B](f:A=>List[A],b:B)=f(b)      // ERROR
def zz[A,B](f:A=>List[A],b:B, a:A)=f(a) // OK

def u [A] (a:A)=a
*/

/*
class Covariant[+A]
val cv1:Covariant[AnyRef] = new Covariant[String]   //OK
val cv2:Covariant[String]= new Covariant[AnyRef]    //ERROR: error: type mismatch;

class Contravariant[-A]
val cv3:Contravariant[String]=new Contravariant[AnyRef] //OK
val cv4:Contravariant[AnyRef]=new Contravariant[String] //ERROR: error: type mismatch;
*/


/*
class Animal { val sound = "rustle" }
class Bird extends Animal { override val sound = "call" }
class Chicken extends Bird { override val sound = "cluck" }
val getTweet: (Bird => String) = ((a: Animal) => a.sound )
*/


/*
def sum : (Int, Int) => Int = {case (i, j) => i + j}
sum(2,1)

var list =List((1, "One"),(2,"Two"),(3, "Three"))
list map{case(d,_)=>d}

expr match {
  case List(1,_,_) => " a list with three element and the first element is 1"
  case List(_*)  => " a list with zero or more elements "
  case Map[_,_] => " matches a map with any key type and any value type "
  case _ =>
}
*/

/*
List (1,2,3).foreach(a=>print(a))
List (1,2,3).foreach(print(_))

List(1,2,3).reduceLeft((a,b)=> a+b)
List(1,2,3).reduceLeft(_+_)
*/


/*
class Person(private var _name: String) {
  def name = _name                             // accessor
  def name_=(aName: String) { _name = aName }  // mutator
}


val p = new Person("Jonathan")
p.name = "Jony"    // setter
println(p.name)    // getter
*/

/*
class Test {
  def fun = {  }
  val funLike1 = fun
  val funLike2 = fun _
}
*/

/*
val sum1= (a: Int, b: Int, c: Int) => a + b + c
List(1,2,3).reduceLeft(_+_)

val sum2 =(x: List[Int]) =>x.reduceLeft(_+_)
def addList[T : Numeric](list: List[T]): T = list.sum

val a=List(1,2,3)
sum2(a)
addList (a)
*/

/*
trait List[T]{
  def isEmpty:Boolean
  def head:T
  def tail: List[T]
}

class Cons[T](val head:T, val tail:List[T]) extends  List[T]{
  def isEmpty = false

}

class Nil[T] extends  List[T]{
  def isEmpty:Boolean = false
  def head:Nothing=throw new NoSuchElementException ("Nil.head")
  def tail:Nothing=throw new NoSuchElementException ("Nil.tail")
}



def nth [T] (n:Int, xs:List[T]):T={
  if (xs.isEmpty) throw  new IndexOutOfBoundsException
  if (n==0) xs.head
  else nth(n-1,xs.tail)
}

val list= new Cons(1, new Cons(2,new Cons(2, new Nil)))
nth(1, list)
//nth(-1, list)
nth(100, list)
*/

/*
def p(implicit i:Int)=print(i)
p // da error pq el compilador no sabe resolver
implicit  val v=2
p     //pinta un 2
p(5)   // pinta un 5

def pp(a:Int)(implicit i:Int) = println(a,i)
pp(11)
*/


/*
val nums = List(1,2,3,-1,-5,6,1)

nums map (x=> x*2)
nums filter (x=>x>0)
nums filterNot (x=>x>0)
nums partition (x=>x>0)
nums takeWhile  (x=>x>0)
nums dropWhile  (x=>x>0)
nums span  (x=>x>0)
*/

/*
val fruits=List("A","A","B","C","C")
def pack[T](xs:List[T]):List[List[T]]=xs match {
  case Nil => Nil
  case x::xs1 =>
      val (first, rest)=xs span (y=> y==x)
      first :: pack(rest)
}
pack(fruits)

def encode[T](xs:List[T]):List[(T,Int)]=
 pack (xs) map (ys=> (ys.head,ys.length))
*/

/*
val nums = List(0,2,3)
nums reduceLeft ((x,y)=> x+y)
nums reduceLeft ( _*_)


(nums foldLeft 0) (_*_)
*/

/*
var A= "Hello World"
var B=List(1,2,3,4)
A zip B
val c = A zip B
c.unzip
c.map (xy => xy._2).sum
c.map {case(_,y)=>y}.sum


A flatMap(c => List('.',c))
*/

/*
val colors = Map("red" -> "1", "azure" -> "2")
val colors2 = Map(("red" , "1"), ("azure" -> "2"))
colors.keys
colors("red")
colors get "ffff"
colors getOrElse("fff","desconocido")
*/

/*
var numbers = List(1,4,5,1,6,5,6)
val leters = List ("AB","A","BB","AA")
numbers.groupBy(x=>x)
numbers.groupBy(x=>x).keys
numbers.groupBy(identity)  //error pero no se pq

leters.groupBy (x =>x.charAt(0))


numbers partition (_>2)
numbers span (_<5)

val (a,b) = numbers partition (_>2)
a
b

numbers = List(1,2,3,4,5,6)
numbers.sliding(2).toList
numbers.sliding(2,2).toList
*/


val colors = Map("red" -> "1", "azure" -> "2").withDefaultValue("XX")
colors("foo")

