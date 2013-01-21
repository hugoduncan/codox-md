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
you can override these in your project by including files with these paths in
your resources.

The classes codox-md uses to insert content are:


#### All Templates
project-title
: Replaced with the project title.

namespace-title
: Replaced with the namespace title.

doc
: Replaced with the project description or namespace documentation.

ns-links ns-list
: Duplicated for each namespace.

ns-links ns-list ns-name
: Content replaced with the name of the namespace being listed.

ns-links ns-list ns-link
: Should reference an HTML `a` element which will provide a link to the
  namespace documentation for each namespace being listed.

#### Index Template

namespace ns-link
: Scope for namespace links.

namespace ns-link ns-link
: Should reference an HTML `a` element which will provide a link to the
  namespace documentation for each namespace listed.

namespace ns-link ns-name
: Content replaced with the name of each namespace.

namespace doc
: Content replaced with the docstring of the namespace.

namespace ns-var-list
: Duplicated for each var in the namespace

namespace ns-var-list ns-var-name
: Content replaced with the name of each var.

namespace ns-var-list ns-var-link
: Should reference an HTML `a` element which will provide a link to the
  var documentation for each var being listed.

#### Namespace Template

ns-var-links ns-var-link
: Duplicated for each var in a namespace. Should contain an HTML `a` element
  which will provide a link to the var documentation.

public var-name
: Content replaced with the name of each var.

public var-type
: The var-type class will be augmented with either a "macro", "fn", or "var"
  class, depending on the type of var.

public usage
: Content replaced with the arglists for each var.

public doc
: Content replaced with the documentation for each var.

public usage
: Content replaced with the arglists for each var.

public doc
: Content replaced with the documentation for each var.

In addition, the following css ids are used:

var-type
: Content replaced with either "macro", "fn", or "var", depending on the type of
  var.

### CSS

To allow you to provide CSS customisation without having to replace the whole of
`codox`' built in CSS, `codox-md` adds a css stylesheet to the templates, which
is taken from the classpath using a `codox/css/codox-md.css` path.

## License

Copyright (C) 2012, 2013 Hugo Duncan

Distributed under the Eclipse Public License.

[codox]: https://github.com/weavejester/codox
