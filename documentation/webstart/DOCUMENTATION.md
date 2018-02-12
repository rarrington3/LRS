# Generating Documentation

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

There are two primary forms of documentation that are generated from the project. The first is for the API and 
derives from the [RAML](./RAML.md) definitions and the second is from [JSDocs](http://usejsdoc.org/) in the source code.

Webstart includes a documentation task to publish both of these. There is even a built-in server to host the 
generated files available and will open the generated docs in a browser:

    grunt
    [?] What task would you like to run? 
      Run the Scaffolding Tools to Build Out Project 
      Run the Web Application 
      Run the Cordova Application 
      Run the API Designer 
      Run the Test Suite 
    ❯ Run the Documentation Generator 
      Publish the Application for Deployment 


Or simply run `grunt docs` or `grunt server:docs` to launch the documentation and rebuild on changes.

To assist in JSDoc generation, we are also leveraging the [typecheck](https://github.com/codemix/babel-plugin-typecheck) 
and [jsdoc-babel](https://www.npmjs.com/package/jsdoc-babel) plugins for Babel. Using type annotations will automatically
assist in generating JSDocs based on your code without actually writing JSDoc blocks.

<pre>
export default class Foo {      //<-- will generate class Foo in JSDoc
    bar:string = 'bar';         //<-- will generate property bar of type string in JSDoc
    
    foo(bar:string):string {    //<-- will generate function foo accepting bar of type string and returning type string in JSDoc
        this.bar = bar;
        return this.bar;
    }
}
</pre>

These type annotations will also assist in code hinting and runtime type checking during development.
