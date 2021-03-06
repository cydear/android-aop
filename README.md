#android-aop

OOP 面向对象编程
AOP 面向切面编程

OOP和AOP都是方法论

OOP：OOP是将功能或者问题模块化，每个模块钻心干自己的事情，模块之间通过设计好的接口交互。
     OOP中会存在有些功能会横跨嵌入到多个模块中。
     
AOP：AOP的目标是把这些分散的功能集中起来，放到统一的地方处理

Android接入AspectJ

dependencies{
    compile 'org.aspectj:aspectjrt:1.8.9'
}

buildscript{
    repositories{
        mavenCentral()
    }
    dependencies{
        classpath 'org.aspectj:aspectjtools:1.8.9'
        classpath 'org.aspectj:aspect-jweaver:1.8.9'
    }
}


Join Points 程序执行点，例如一个函数的调用，设置或者获取变量的值 set,get方法
            切面插入的位置就是程序的执行点,就是可插入切面的位置

# JooinPoints执行点类型如下：

method call  函数调用  Log.e
method execution  函数执行  Log.e的内部执行
constructor call 构造函数调用
constructor execution 构造函数执行 构造函数内部执行
field get 获取某个变量
field set 设置摸个变量
pre initialization Object在构造函数中做得一些工作
initialization Object在构造函数中做得工作
static initialization 类初始化  static{ }
handler 异常处理  对应catch内的执行
advice execution hook的代码，我们定义的代码

#Pointcuts 提供一种方法使得开发者能够选择自己感兴趣的JoinPoints
public pointcut testAll():call(public * *.println(..))&&!within(TestAspect);

public ：表示这个pointcut是public访问
pointcut：关键词，表示这里定义的是一个pointcut.
testAll()：pointcut的名字，使用pointcut，直接使用它testAll()名字
testAll()后:冒号：冒号后是pointcut选择Joinpoint的条件

#通配符
call(public * *.println(..)) 

call 表示选择Joinpoint类型为call类型，执行点是方法的调用时 Log.e

#public * *.println(..)&&!within(TestAspect) 通配符

public 表示目标JPoint的访问类型(public/private/protect)
第一个*号：表示返回值类型是 任意类型
第二个*号：指定包名，*表示不限定包名
(..)：代表人任意个参数，任意个参数的类型
&&，||，!

within()类型选择方法，表示满足某个条件

#pointcuts 和 JoinPoint类型的映射

Method execution  ==> execution(MethodSignature)
Method call       ==> call(MethodSignature)
Constructor execution ==> execution(ConstructorSignature)
Constructor call  ==> call(ConstructorSignature)
Class initialization  ==> staticinitialization(TypeSignature)
Field read access  ==> get(FieldSinature)
Field write access  ==> set(FiledSinature)
Exception handler execution  ==> handler(TypeSignature)
Object initialization  ==> initialization(ConstructorSiganture)
Object pre-initialization  ==> preinitialization(ConstructSignature)
Advice execution  ==> adviceexecution()

#Method Signature 完整表达式

@注解 访问权限 返回值类型 包名.函数名(参数)

访问权限  *  不限定类型

包名.函数名  查找匹配的函数

* 匹配除.号之外的任意字符
.. 表示任意的子package
+ 表示子类

java.*.Date 可以表示java.sql.Date , java.util.Date

Test*  可以表示TestBase , 也可以表示TestDerived

java..* 表示java任意子类
java..*Model+  表示java任意package中名字以Model结尾的子类，比如 TableModel,TreeModel

#函数参数
(int,char) 表示参数只有两个，int  string
(String,..) 表示至少有一个参数，并且第一个是String类型，后面参数类型不限
.. 代表任意参数个数和类型
(Object ...) 表示不定个数的参数，且类型都是Object，这里...不是通配符，代表不定参数

#Constructor Signature 构造函数和Method类似，只不过构造函数没有返回值，函数名必须叫new

#public *..TestDerived.new(..)

public 选择public 访问权限

TestDerived.new  代表TestDerived的构造函数

(..) 代表参数个数和类型都是任意的

#Field Signature 和 Type Signature

@注解  访问权限 类型 类名.成员变量

@注解和访问权限是可选的

类型：成员变量类型，*代表任意类型
类名.成员变量名：成员变量名可以是*,代表任意成员变量

set(inttest..TestBase.base) 表示设置TestBase.base变量时的JPoint

#Type Signature

staticinitialization(test..TestBase) 表示TestBaes类的static block
handler(NullPointerException) 表示catch到NullPointException的Jpoint

#其他常用选择JoinPoint方法

within(TypePattern)  TypePattern标示package或者类，可以使用通配符。  within(Test) 标示某个package或者类中的所有JPoint

withincode(Constructor Signature|Method Signature) 表示某个构造函数或其他函数执行过程中涉及到的JPoint。 

withinCode(* TestDerived.testMethod(..)) 表示testMethod涉及的JPoint 
withinCode(*.Test.new(..))  表示Test构造函数涉及的JPoint

cflow(pointcuts)  cflow是call flow的意思，cflow的条件是一个pointcut

cflow(call TestDerived.testMethod) 表示调用TestDerived.testMethod函数时所包含的JPoint,包括testMethod的call这个JPoint本身。

cflowbelow(pointcuts)  cflow是call flow的意思

cflowblow(call TestDerived.testMethod) 表示调用TestDerived.testMethod函数时所包含的JPoint，不包括testMethod的call这个JPoint本身

this(Type) JPoint的this对象是Type类型  obj instanceof Type

this(TestMethod) 将会选中testMethod JoinPoint

target(Type) JPoint的target对象是Type类型

和this相对的是target。不过target一般用在call的情况。call一个函数，这个函数可能定义在其他类。比如testMethod是TestDerived类定义的。那么
target(TestDerived)就会搜索到调用testMethod的地方。但是不包括testMethod的execution JPoint

args(TypeSignature) 用来对JPoint的参数进行条件搜索

args(int,..) 表示第一个参数是int,后面参数个数和类型不限的JPoint

#Advice 是在执行点JoinPoint执行前后设置的代码就是advice, advice是一种hook

before():testAll(){
    System.out.println("before calling："+thisJoinPoint);
    System.out.println("  at:"+thisJoinPoint.getSourceLocation());
}

testAll()是前面定义的pointcuts,而before()定义了在这个pointcuts选中的JPoint执行前我们要干的事情

#Advice 类型

before()  before advice   表示在JPoint执行之前，需要干的事情
after()   after advice    表示JPoint自己执行完了后，需要干的事情
after():returning  返回值类型  returning和throwing后面都可以指定具体的类型，如果不指定类型的话则匹配的时候不限定类型
after():throwing  异常类型
返回值类型 around()  before和around是指JPoint执行前或执行后被触发，而around就替代了原JPoint   
around是替代了原JPoint，如果要执行原JPoint的话，需要调用proceed


#总结

JPoint  一个程序执行的关键执行点

pointcuts  提供一种方法来选择目标JPoint。程序有很多JPoint,但是需要一种方法来你选择我们关注的JPoint。方法就是利用pointcuts来完成

Advice   通过pointcuts选择目标JPoint后，就用advice注入我们要做的事情。 before after around
















