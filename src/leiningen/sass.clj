(ns leiningen.sass
  (:use [leiningen.lein-common.lein-utils :only [lein2?]])
  (:require [leiningen.tasks   :as tasks]
            [leiningen.help    :as lhelp]
            [leiningen.clean   :as lclean]
            [leiningen.compile :as lcompile]
            [leiningen.deps    :as ldeps]
            [robert.hooke      :as hooke]))

(tasks/def-lein-task sass)

(defn activate
  "Set up hooks for the plugin.  Eventually, this can be changed to just hooks,
   and people won't have to specify :hooks in their project.clj files anymore."
  []
  (hooke/add-hook #'lcompile/compile (tasks/standard-hook :sass :once))
  (hooke/add-hook #'lclean/clean     (tasks/standard-hook :sass :clean))
  (hooke/add-hook #'ldeps/deps       (tasks/standard-hook :sass :deps)))

; Lein1 hooks have to be called manually, in lein2 the activate function will
; be automatically called.
(when-not lein2? (activate))
