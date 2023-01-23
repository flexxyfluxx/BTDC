package de.wvsberlin.test.main

import de.wvsberlin.factory.PyObjFactory
import de.wvsberlin.factory.interfaces.*

fun main() {
    val myFactory = PyObjFactory(HelloWorldSayerType::class.java, "test", "HelloWorldSayer")

    val myHelloWorldSayer = myFactory.make() as HelloWorldSayerType

    myHelloWorldSayer.sayHelloWorld()
}