# NoobDoc Documentation

NoobDoc is a kotlin program extracting your code business documentation directly from your inline comments following a specific format.

## [NoobDoc-01]-Project file tree building
### NoobDocFetching
- [NBFetch]-NoobDoc will loop recursively on each of your folder
- [NBFetch-00]-For each entry in each folder, if the extension match with the one passed in programm argument NoobDoc will generate a documentation for this file
- [NBFetch-01]-If NoobDoc find folder in your folders, it will loop into them too and document the valid one


## [NoobDoc-02]-NoobDoc Tag Finder
### 01-Title and description
- [TD01]-NoobDoc title follow pattern "[LanguageInlineCommentSymbol]@[yourDoctag]DocTitle("Your doc title")"
- [TD02]-If no title is found in the project, default title will be "NoobDoc"
- [TD03]-NoobDoc description follow pattern "[LanguageInlineCommentSymbol]@[yourDoctag]DocDescription("Your doc description")"
- [TD04]-If no description is found in the project, none will be displayed

### 02-Section Finder
- [SF00]-NoobDoc section follow pattern "[LanguageInlineCommentSymbol]@[yourDoctag]DocSection("[Section Code]", "[Section Name]")", example are available at rule[@TE01]
- [SF01]-If no section tag can be found, noob doc will use current file name (without its extension) as section name and N00B-[incremental number] as code
- [SF02]-Section will be displayed in alphabetical order following [Section Code] param

### 03-Rule Finder
- [RF00]-NoobDoc rules follow pattern [LanguageInlineCommentSymbol]@[yourDoctag]Doc("[Rule Description]", "[Rule Code]", "[Rule Group]"), example are available at rule [@TE01]
- [RF01]-Noob doc param are found using regexp "(?\<param\>.+?)"((,\W)|$), sometime double quote and coma combination
- [RF01]-can fail, use instead their UTF-8 Ascii code %22 and %2C
- [RF01]-but it's a rare problem that should happen only when you want to document NoobDoc using NoobDoc
- [RF02]-NoobDoc rules should contains at least 3 params according to [@RO1] (optional additional parameters could come with future release), if not rule is invalid and will not be displayed
- [RF03]-NoobDoc works only with inline comment, but if you have the feeling that your description is too long you can write the rest of it in another noobDocTag
- [RF03]-just give them the same rule code and group and noob doc will display them as one rule in their order of apparition adding the spacing by itself

### 04-NoobDoc Tag exemple
- [TE01]-java/php/C# with "noob" tag will be //@noobDoc("rule description", "rule code", "rule set name") 
- [TE02]-python with "monty" tag will be #@montyDoc("rule description", "rule code", "Crule set name") 
- [TE03]-If you want an exemple of a documentation generated with NoobDoc, you're actually reading one

