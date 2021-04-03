This is a reference project I use for take home assignments.

It divides its features in Gradle modules. This structure makes clear the boundaries
of each module, e.g. the `:products:list` module has only the dimens.xml, strings.xml, layout files,
et al., that it cares about.

Some testing was added to the project, but it is by no means full coverage. I added a few UI
tests to the `:products:list` module and a few database-related tests to the `:products:local` module.

Google's opinionated MVVM architecture is used since it works nicely with Google's APIs (i.e. the
androidx artifacts).
