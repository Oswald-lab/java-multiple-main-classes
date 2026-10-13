# 06-java-multiple-main-classes
Three classes with `public static void main` in different packages:
  com.octiq.test.alpha.AlphaApp, com.octiq.test.beta.BetaApp, com.octiq.test.tools.ToolsRunner
No mainClass / Main-Class / application plugin in either build file.
Expected: import succeeds; tool should flag ambiguity (list candidates or ask which to run),
not silently pick one or fail.
