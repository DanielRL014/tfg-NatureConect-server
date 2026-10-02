import sys
import json
import cv2
from ultralytics import YOLO
import os

# Parámetros
CONFIDENCE_THRESHOLD = 0.75

# Cargar modelos
modelo_det = YOLO("src/main/python/detector.pt", verbose=False)        # Modelo de detección
modelo_cls = YOLO("src/main/python/clasificador.pt", verbose=False)    # Modelo de clasificación
nombres_clases = modelo_cls.names

def clasificar_crop(crop):
 temp_path = "temp_crop.jpg"
 cv2.imwrite(temp_path, crop)

 res = modelo_cls(temp_path)[0]
 os.remove(temp_path)

 probs = res.probs
 clase_idx = int(probs.top1)
 conf = float(probs.top1conf)
 nombre_clase = nombres_clases.get(clase_idx, f"clase_{clase_idx}")

 return {
     "clase": nombre_clase,
     "confianza_clasificacion": conf
 }

def procesar_imagen(ruta_img):
 img = cv2.imread(ruta_img)
 detecciones = modelo_det(ruta_img)[0]

 resultado = {
     "imagen": ruta_img,
     "objetos": []
 }

 for box in detecciones.boxes:
     conf = box.conf.item()
     if conf >= CONFIDENCE_THRESHOLD:
         x1, y1, x2, y2 = map(int, box.xyxy[0])
         crop = img[y1:y2, x1:x2]

         clasificacion = clasificar_crop(crop)

         resultado["objetos"].append({
             "bbox": [x1, y1, x2, y2],
             "confianza_deteccion": conf,
             **clasificacion
         })

 print(json.dumps(resultado))

if __name__ == "__main__":
 if len(sys.argv) < 2:
     print("Uso: python procesar_yolo.py <imagen>", file=sys.stderr)
     sys.exit(1)

 ruta = sys.argv[1]
 procesar_imagen(ruta)
