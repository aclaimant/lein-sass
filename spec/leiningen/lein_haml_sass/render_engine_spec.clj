(ns leiningen.lein-haml-sass.render-engine-spec
  (:use [speclj.core])
  (:require [leiningen.lein-haml-sass.render-engine :as engine]))

(describe "render-engine"

  (describe "fn render"
    (with-all ensure-engine-started! #'engine/ensure-engine-started!)
    (with-all ruby-scripting-container #'engine/c)

    (context "without options"
      (before-all
        (dosync (ref-set @@ruby-scripting-container nil))
        (@ensure-engine-started! {}))

      (it "render the haml template correctly using haml gem"
        (let [template "%html.a-class" ]
          (should= "<html class='a-class'></html>\n"
                   (engine/render :haml template))))

      (context "sass"
        (it "render the sass template correctly using sass gem"
          (let [template "
.my-class
  display: none" ]
            (should= ".my-class {\n  display: none; }\n"
                     (engine/render :sass template)))))

      (it "render the scss template correctly using sass gem"
        (let [template "
.my-class {
  display: none;
}" ]
          (should= ".my-class {\n  display: none; }\n"
                   (engine/render :scss template)))))

    (context "with options"
      (before
        (dosync (ref-set @@ruby-scripting-container nil))
        (@ensure-engine-started! {:style :compressed}))

      (it "uses the correct style to render sass"
        (let [template "
.my-class
  display: none" ]
          (should= ".my-class{display:none}\n"
                   (engine/render :sass template))))

      (it "uses the correct style to render scss"
        (let [template "
.my-class {
  display: none;
}" ]
          (should= ".my-class{display:none}\n"
                   (engine/render :scss template)))))))
