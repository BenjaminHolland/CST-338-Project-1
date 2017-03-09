# CST-338-Project-1
Educational Database System

##Goal
Given the acceptance tests in the design document (project1.pdf), create classes Database, School, Instructor, and Course that pass the tests. 

##Design
There were two major design decisions to make: How to store the data, and how to implement the methods required by the design document.

For the first, I decided to create an in-memory relational database class instead of an object heirarchy. This kept each record object implementation extremely simple and decoupled, allowing for rapid changes in relationships when on several occasions I realised I had misunderstood the design document. It also allowed for a more streamlined mental model of the data, which helped when designing tests. 

For the second, I decided to provide adapters around the database rather than use the database and the data objects directly. This solved 3 problems:

1. Using the data classes directly would complicate the data objects with behavior that they shouldn't have. A Student record shouldn't know how to find out what courses it's enrolled in. By creating an adapter specifically for the UI, I can add behavior to the adapter without changing anything in the underlying data model. 

2. Because of the way the acceptance tests in the assignment are set up, the methods defined are essentially untestable programmatically without either creating explicit testing methods on the object or scraping off System.out. By separating the data from the UI concerns, I was able to do rigerous testing on the database behavior in order to be reasonably sure that the much simpler UI adapter functions were working correctly.

3. Going with UI adapters allows the code to be more maintainable, and much easier for multiple people to work on. While maintainability was not a direct concern for this project, It's still an importaint facet of any project. 

## Issues
While I feel like this project is, for the most part, relativly well structured and competent, there are still a number of issues that could be resolved with additional time. Note that some of these are mirrored in the Issues section of github.

1. The constraint that each class may only have 1 teacher is hacked in, the behavior does not follow the convention set up by the API, and the test is not particularly effective. Failures of this constraint should throw an exception rather than silently updating, and tests for this should be made. However, this was not done due to time constraints, and the acceptance tests do not define this behavior. 

2. Integer instead of int is used in most places. During code review it was brought up that this impacts performance and is not good java practice. This was done because of my non-farmiliarity with Java, and my natural and somewhat compulsive aversion to mixed-camel typing. A pass replacing most methods returning Integer instead of int, and most fields using Integer instead of int, should be replaced.

3. Integers are used as primary keys when accessing the database. This can cause confusion to a caller as to what exactly is expected. There are two alternatives: Make separate strongly typed key classes for each entity, or use incomplete entities as keys. While I considered both of these, I decided ultimately to go with integer keys for simplicity, and to match the design documents specifications. With more time, I'd probably choose to use incomplete records as keys. Though my first instinct is to make strongly typed key objects, this doubles the number of required classes, and the method doesn't scale well. 

4. The use of exceptions seems...odd. Since every constraint failure in the database throws it's own type of exception, it seems like it clutters up the project with exception class definitions. Throw in that most exceptions must be delcared on the prototype and either handled or declared on callers, and I've made a system that seems overly verbose when it comes to error states. I'm not entirely sure how to solve this problem, either, as the reading i've done indicate that error codes are generally considered bad practice (in java). I've done some work by making more general constraint failure exceptions, which should lower the workload on the caller, but pollutes the project even further. I could extend each method to take some sort of error tracking object that could be checked after each call, but this just seems like an advanced error code. Adding constraint specifics to each general exception is another option, but that increases the workload of the caller, requiring them to switch on the type of entity referred to. 





