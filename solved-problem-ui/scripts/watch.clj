(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'solved-problem-ui.core
   :output-to "out/solved_problem_ui.js"
   :output-dir "out"})
