# codox-md

Provide templated markdown output for [codox][codox].

## Usage

To enable use of codox-md with codox, install it as a plugin.

```bash
lein plugin install codox-md 0.1.0
```

In your `project.clj` configure codox to use the plugin.

```clojure
:codox {:writer codox-md.writer/write-docs}
```

### Templates

`codox-md` looks for HTML templates as resources. It uses
`codox/index_template.html` for the project index page, and
`codox/ns_template.html` for each namespace. Default templates are included, but
you can override these in your project.

The classes codox-md uses to insert content are:

project-title
: Replaced with the project title.

namespace-title
: Replaced with the namespace title.

doc
: Replaced with the project description or namespace documentation.

ns-links ns-link
: Duplicated for each namespace. Should contain an HTML `a` element which will
  provide a link to the namespace documentation.

namespace ns-link
: A link for the current namespace. Should contain an HTML `a` element which
  will provide a link to the namespace documentation.

public usage
: Content replaced with the arglists for each var.

public doc
: Content replaced with the documentation for each var.

ns-var-links ns-var-link
: Duplicated for each var in a namespace. Should contain an HTML `a` element
  which will provide a link to the var documentation.

public h3
: Content replaced with the name of each var.

public usage
: Content replaced with the arglists for each var.

public doc
: Content replaced with the documentation for each var.


## License

Copyright (C) 2012 Hugo Duncan

Distributed under the Eclipse Public License.

[codox]: https://github.com/weavejester/codox
