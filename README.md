<h1>JSoup Library Usages Testing & Demonstration <br> (Personal project)</h1>

[Jsoup](https://jsoup.org/) : Java HTML Parser</b>
<br>"jsoup is a Java library that simplifies working with real-world HTML and XML.
It offers an easy-to-use API for URL fetching, data parsing, extraction, and manipulation using DOM API methods, CSS, and xpath selectors." [[1]](#1).

<h2>About this project</h2>
This is a testing and learning project preceding a crossword puzzle generator using cambridge dictionary online. <br>
It uses the JSoup library to navigate the website and retrieve data; In this case I'm using it to randomly choose a letter and a word (starting with the letter). <br>
I also get the meaning and type of word (noun,adjective, ...).
<br><br>
<h3>Downsides</h3>
I'm navigating the website by searching html elements by their class. This poses 2 problems for long term projects:<br>
- You have to manually find the classes names with google dev tools. <br>
- If the website changes the class name, the project can't find the elements; So you'd have to go look for and change the class names in the code.
<br><br>

<i>Give that JSoup searches based on html elements and attributes, I haven't found a way arround this issue. Changes in the website structure will result in having to change our code, unfortunately. If I find a way arround this, I will update the project, but so far I believe it's unlikely...</i>

## References
<a id="1">[1]</a> 
https://jsoup.org/
