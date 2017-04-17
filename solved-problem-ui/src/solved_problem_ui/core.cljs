(ns solved-problem-ui.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn get-workspace-dimensions []
  {:x (.-innerWidth js/window)
   :y (.-innerHeight js/window)})

(def stroke-weight 2)
(def com-length 6)

(defn setup []
  (q/frame-rate 10)
  (q/background 250)
  (q/stroke-weight stroke-weight)
  (q/stroke-join :round)
  {:workspace (q/create-graphics (:x (get-workspace-dimensions)) (:y (get-workspace-dimensions)))})

(defn get-toolbar-position [workspace-dims toolbar-height]
  (-> workspace-dims
      (assoc :x 0)
      (update :y #(- % toolbar-height))))

(defn get-toolbar-elements-start-position
  [toolbar-pos toolbar-height]
  (let [element-start-offset (* 20 com-length)]
    (-> toolbar-pos
        (update :x #(+ % element-start-offset))
        (update :y #(+ % (/ toolbar-height stroke-weight))))))

(defn get-toolbar-element-position
  [toolbar-pos toolbar-height elem-index]
  (let [elem-pitch (* com-length 5)
        elem-width (* com-length 5)]
    (update (get-toolbar-elements-start-position toolbar-pos toolbar-height)
            :x #(+ % (* (+ elem-pitch elem-width) elem-index)))))

(defn draw-v [pos]
  (q/push-matrix)
  (q/translate (:x pos) (:y pos))
  (q/rotate (q/radians -30))
  (q/line 0 0 (* 2 com-length) 0)
  (q/rotate (q/radians 60))
  (q/line 0 0 (* 2 com-length) 0)
  (q/pop-matrix))

(defn draw-resistor [pos]
  (let [seg-length (* 1.3 com-length)]
    (q/translate (:x pos) (:y pos))
    (q/begin-shape)
    (q/vertex (+ (/ seg-length 2) (/ stroke-weight 2)) (* 4.5 seg-length))
    (q/vertex (+ (/ seg-length 2) (/ stroke-weight 2)) (* 2.5 seg-length))
    ;;(q/vertex seg-length (* 3 seg-length))
    (q/vertex 0 (* 2 seg-length))
    (q/vertex seg-length seg-length)
    (q/vertex 0 0)
    (q/vertex seg-length (- seg-length))
    (q/vertex 0 (* -2 seg-length))
    (q/vertex seg-length (* -3 seg-length))
    (q/vertex (+ (/ seg-length 2) (/ stroke-weight 2)) (* -3.5 seg-length))
    (q/vertex (+ (/ seg-length 2) (/ stroke-weight 2)) (* -4.5 seg-length))
    (q/vertex (+ (/ seg-length 2) (/ stroke-weight 2)) (* -5 seg-length))
    (q/end-shape))
  ;; (draw-v pos)
  ;; (draw-v (update pos :y #(+ % (* com-length 2 0.866) stroke-weight)))
  ;; (draw-v (update pos :y #(+ % (* 2 com-length 2 0.866) (* 2 stroke-weight))))
  ;; (q/push-matrix)
  ;; (q/translate (+ (:x pos) (* com-length 2 0.866)) (- (:y pos) (* com-length)))
  ;; (q/rotate (q/radians -145))
  ;; (q/line 0 0 com-length 0)
  ;; (q/rotate (q/radians 15))
  ;; (q/line 0 0 com-length 0)
  ;; (q/pop-matrix)
  )

(defn draw-minus [pos]
  (q/line (- (:x pos) (/ com-length 2)) (:y pos)
          (+ (:x pos) (/ com-length 2)) (:y pos)))

(defn draw-plus [pos]
  (draw-minus pos)
  (q/push-matrix)
  (q/translate (:x pos) (:y pos))
  (q/rotate q/HALF-PI)
  (draw-minus {:x 0 :y 0})
  (q/pop-matrix))

(defn draw-vsource [pos]
  (let [center-x (:x pos)
        center-y (:y pos)
        circle-diameter (* 8 com-length)
        circle-radius (/ circle-diameter stroke-weight)
        connection-length (* 2 com-length)]
    ;; Circle
    (q/ellipse center-x center-y circle-diameter circle-diameter)
    ;; Connection points
    (q/line center-x (- center-y circle-radius (inc stroke-weight))
            center-x (- (- center-y circle-radius) connection-length (inc stroke-weight)))
    (q/line center-x (+ center-y circle-radius stroke-weight)
            center-x (+ (+ center-y circle-radius) connection-length stroke-weight))
    ;; +/-
    (draw-minus {:x center-x :y (+ center-y (/ circle-radius 2))})
    (draw-plus {:x center-x :y (- center-y (/ circle-radius 2))})))

(defn draw-toolbar []
  (let [toolbar-height (* 20 com-length)
        workspace-dims (get-workspace-dimensions)
        toolbar-pos (get-toolbar-position workspace-dims toolbar-height)]
    (q/rect (:x toolbar-pos) (:y toolbar-pos) (dec (:x workspace-dims)) (dec toolbar-height))
    (draw-vsource (get-toolbar-element-position toolbar-pos toolbar-height 0))
    (draw-resistor (get-toolbar-element-position toolbar-pos toolbar-height 1))))

(defn draw-components []
  )

(defn draw []
  (draw-toolbar))

(q/defsketch workspace
  :host "workspace"
  :draw draw
  :settings q/smooth
  :setup setup
  :size [(:x (get-workspace-dimensions))
         (:y (get-workspace-dimensions))]
  :middleware [m/fun-mode]
  :features [:global-key-events])
