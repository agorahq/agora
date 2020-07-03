const initialMarkdown = `
## H2 header

This is a paragraph, nothing fancy.

- And this
- Is
- A
- List

1. and a
2. numbered list
`.trim();

const markdownit = window.markdownit({
  html: true,        // Enable HTML tags in source
  xhtmlOut: true,        // Use '/' to close single tags (<br />).
  // This is only for full CommonMark compatibility.
  breaks: false,        // Convert '\n' in paragraphs into <br>
  langPrefix: 'language-',
  linkify: false,        // Autoconvert URL-like text to links
  typographer: false, // Enable some language-neutral replacement + quotes beautification
  // Double + single quotes replacement pairs, when typographer enabled,
  // and smartquotes on. Could be either a String or an Array.
  //
  // For example, you can use '«»„“' for Russian, '„“‚‘' for German,
  // and ['«\xA0', '\xA0»', '‹\xA0', '\xA0›'] for French (including nbsp).
  quotes: '“”‘’',
  // Highlighter function. Should return escaped HTML,
  // or '' if the source string is not changed and should be escaped externally.
  // If result starts with <pre... internal wrapper is skipped.
  highlight: function (/*str, lang*/) { return ''; }
});

const turndownService = new TurndownService({
  headingStyle: "atx",
  bulletListMarker: "-",
  codeBlockStyle: "fenced",
  fence: "```",
  emDelimiter: "*",
  strongDelimiter: "**",
  linkStyle: "inlined",
  linkReferenceStyle: "full"
});

const tinySelector = "#tinymce";

window.addEventListener("DOMContentLoaded", (event) => {

  let initialHtml = markdownit.render(initialMarkdown);
  console.log(`Initial HTML: \n${initialHtml}`);

  tinymce.init({
    selector: tinySelector,
    plugins: "lists",
    toolbar: "undo redo | styleselect | bold italic | bullist numlist",
    menubar: "",
    setup: (editor) => {
      editor.on('init', (e) => {
        editor.setContent(initialHtml);
      });
    }
  });

  document.getElementById("go").addEventListener('click', event => {
    runConversion();
  });

});

let runConversion = () => {

  let htmlResult = tinymce.activeEditor.getContent();

  document.getElementById("html-result").innerHTML = htmlResult;

  let markdownResult = turndownService.turndown(htmlResult);
  document.getElementById("markdown-result").innerHTML = markdownResult;
}