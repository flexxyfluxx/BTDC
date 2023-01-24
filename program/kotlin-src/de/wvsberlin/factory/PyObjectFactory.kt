package de.wvsberlin.factory

import org.python.core.Py
import org.python.core.PyObject
import org.python.core.PySystemState

/**
 * Factory-Klasse, um Jython-Klassen in Kotlin zu instantiieren.
 * nach https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/#making-use-of-a-loosely-coupled-object-factory
 * von Java zu Kotlin übersetzt
 */
class PyObjFactory {
    private lateinit var theClass: PyObject
    private lateinit var interfaceType: Class<*>

    // init w/ existing python sysstate
    constructor (interfaceType: Class<*>, systemState: PySystemState, moduleName: String, className: String) {
        loadClass(interfaceType, systemState, moduleName, className)
    }

    // init w/ new internal sysstate
    constructor(interfaceType: Class<*>, moduleName: String, className: String) {
        loadClass(interfaceType, PySystemState(), moduleName, className)
    }

    // load class from Python
    fun loadClass(interfaceType: Class<*>, systemState: PySystemState, moduleName: String, className: String) {
        this.interfaceType = interfaceType

        // basically just get the class. from the python system state,idk.
        // read the code and you see how
        val importer = systemState.getBuiltins().__getitem__(Py.newString("__import__"))
        val module = importer.__call__(Py.newString(moduleName))
        theClass = module.__getattr__(className)
    }

    // instantiate and return loaded class

    fun make(vararg args: Array<Any>): Any {
        val convertedArgs: Array<PyObject> = (args.map { Py.java2py(it) }).toTypedArray()

        return theClass.__call__(convertedArgs).__tojava__(interfaceType)
    }

    fun make(vararg args: Array<Any>, keywords: Array<String>): Any {
        val convertedArgs: Array<PyObject> = (args.map { Py.java2py(it) }).toTypedArray()

        return theClass.__call__(convertedArgs, keywords).__tojava__(interfaceType)
    }
}
