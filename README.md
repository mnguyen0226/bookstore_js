# bookstore_js
Designing an online bookstore using Java, TypeScript, Vue.js, MySQL, HTML5, and CSS

TODO:
- Resize the book with centure position (index.html)
- Clean up CSS

Question 1: Grading Rubric
- Will you also compare your submission with the figma design we have for p1?
- Question: Grading Search button should not be right next to search box (-1)? Where should it be? 
- Search button has incorrect placement (-1)
- Queston: Shold the main page has navbar category as well?
> TODO: Check rubric prior to submission

Question 2: Question: In CSS file, the styling is very clean. I found myself starting with create a lot of style for each element, I try to group them for specific element but end up split again so that I can reuse specific styling.

I end up having 1 div with 4-6 classes of CSS, is this number too high? Do you recommend any tools or guideline to write a cleaner CSS code?
> TODO: Clean CSS

Question 4:  activate a and li at the same time flipping color
> TODO: Code CSS

## How to Read VueJS Vite Starter Code-base
- Vue is a single page application `(index.html)` and Vue will swap the home page and the about page.
- component should have more than 1 word long (like HomeView, not Home)
- src/components/icons to store individual items.
    - template: html, script: ts, style: css. Make sure script section on top.
- src/components/router
    - written in .ts.
    - routes property: swap out component for you.
- src/components/stores
- src/components/views
- types.ts:
    - export book item and category item and tell the types of each item.
- main.ts:
    - Build App.vue
    - Import Global CSS
- App.vue:
- db.json: store all the local data. Have 4 categories and 4 books at least.

TODO:
- Check the rubric, make sure the links is correct
- Make sure the style of the footer make sense like underline when choose
- Change category CSS (Piazza)