package umsa.capricornio.utilitarios.herramientas;

import java.io.*;

/**
* Singleton para el tratamiento de archivos de texto y serializacion de objetos<br>
* Para el tratamiento de archivos de texto se dan tres metodos:<br>
* <b>escribirArchivo</b><br>
* <b>leerArchivo</b><br>
* <b>copiarArchivo</b><br>
* <br>
* Para la serializacion de objetos se proporcionan dos metodos:<br>
* <b> guardarObjeto</b><br>
* <b> recuperarObjeto</b><br>
*
* @author Adolfo Vera Blasco
* @version 1.0
*/
public class Archivos {
        /** Clase para acceder al sistema de archivos */
        //private File archivo;
        /** Lector para archivos de texto */
        private FileReader in;
        /** Escritor para archivos de texto */
        private FileWriter out;
        /** Flujo de escritura para objetos */
        private FileOutputStream fos;
        /** Escritor de objetos */
        private ObjectOutputStream oos;
        /** Flujo de lectura  para objetos */
        private FileInputStream fis;
        /** Lector de objetos */
        private ObjectInputStream ois;
        /** Instancia de la clase */
        private static Archivos archivos;

        /**
        * Contructor de la clase
        */
        private Archivos() {
                super();
        }

        /**
        * Comprobamos si existe alguna instancia de la clase y ala devolvemos, en caso
        * contrario esta es la primera vez que se instancia la clase, por lo que creamos
        * el objeto y lo devolvemos.
        *
        * @return Una instancia a esta clase
        */
        public static Archivos getInstance() {
                if(archivos != null) {
                        return archivos;
                } else {
                        archivos = new Archivos();
                        return archivos;
                }
        }

        /**
         * Guarda un archivo de texto en disco
         *
         * @param ruta Path donde guardamos el archivo
         * @param contenido El contenido del archivo de texto
         * @throws IOException Errores en el flujo de escritura para el archivo
         */
        public synchronized void escribirArchivo(String ruta, String contenido) throws IOException {
                File file = new File(ruta);
                out = new FileWriter(file.getAbsolutePath(), true);

                out.write(contenido);
                out.close();
        }

        /**
         * Leemos un archivo y obtenemos los datos contenidos en el.
         *
         * @param ruta Path donde se encuentra el archivo
         * @return Un cadena con el contenido del archivo de texto
         * @throws IOException Error en el flujo de lectura del archivo
         * @throws FileNotFoundException El archivo no se encuentra en la ruta especificada
         */
        public synchronized String leerArchivo(String ruta) throws IOException, FileNotFoundException {
                StringBuffer cadena = new StringBuffer();
                in = new FileReader(ruta);

                int c = 0;
                while((c = in.read()) != -1) {
                   cadena.append((char) c);
                }

                return cadena.toString();
        }

        /**
        * Copia un archivo
        *
        * @param origen Path al archivo de origen
        * @param destino Path al archivo de destino
        * @param annadir Indicamos si a�adimos al final del archivo de destino (true),
        *	o por el contrario machacamos el contenido del archivo de destino (false)
        * @throws IOException Errores en el flujo I/O
        * @throws FileNotFoundException No se encuentra el archivo origen
        */
        public synchronized void copiarArchivo(String origen, String destino, boolean annadir) throws IOException, FileNotFoundException {
                File archivoOrigen = new File(origen);
                File archivoDestino = new File(destino);

        in = new FileReader(archivoOrigen.getAbsolutePath());
        out = new FileWriter(archivoDestino.getAbsolutePath(), annadir);

        int caracter = 0;
        while ((caracter = in.read()) != -1) {
                out.write(caracter);
            }

        in.close();
        out.close();
        }

        /**
        * Serializa un objeto a disco
        *
        * @param ruta Path donde guardar el objeto serializado
        * @param objeto El objeto que se quiere serializar
        * @throws IOException Error en el flujo de escritura del objeto
        */
        public synchronized void guardarObjeto(String ruta, Object objeto) throws IOException {
                fos = new FileOutputStream(ruta);
                oos = new ObjectOutputStream(fos);

                oos.writeObject(objeto);

                fos.flush();
        }

        /**
        * Recuperamos un objeto serializado en disco
        *
        * @param ruta Path donde se encuentra el archivo con el objeto serializado
        * @return Un Object que encapsula al objeto serializado
        * @throws IOException Error en el flujo de lectura del archivo
        * @throws FileNotFoundException El archivo no se encuentra en la ruta especificada
        * @throws ClassNotFoundException Error al recuperar el objeto
        */
        public synchronized Object recuperarObjeto(String ruta) throws IOException, FileNotFoundException, ClassNotFoundException {
                fis = new FileInputStream(ruta);
                ois = new ObjectInputStream(fis);

                return ois.readObject();
        }

  /*      public static void main(String[] args) {
                if(args.length != 0) {
                        System.err.println("La aplicacion no admite parametros");
                } else {
                    
                // recuperamos la instancia de la clase Archivos
                Archivos archivo = Archivos.getInstance();

                // guardamos un archivos
                try {
                        // a medida que ejecutemos esta clase se a�adiran lineas al archivo
                        archivos.escribirArchivo("c:/prueba.txt", "Esto es una prueba de escritura de archivos\n");
                } catch(IOException ex) {
                        System.err.println("Error al escribir el archivo");
                }

                // leemos el contenido del archivo
                String lectura="";
                try {
                        lectura = archivo.leerArchivo("c:/prueba.txt");
                        System.out.println("Contenido del archivo:\n " + lectura);                        
                        //Runtime.getRuntime().exec("print c:\\prueba.txt");
                } catch(FileNotFoundException ex) {
                        System.err.println("No se encuentra el archivo");
                } catch(IOException ex) {
                        System.err.println("Error en flujo I/O");
                }

                // copiamos el archivo
                try {
                        archivos.copiarArchivo("prueba.txt", "copia.txt", false);
                        System.out.println("contenido de la copia:\n " + archivos.leerArchivo("copia.txt"));
                } catch(IOException ex) {
                        System.err.println("Error al copiar.\n" + ex.toString());
                }


                // Serial de objetos
                String serial = "Serialzo esta cadena.";
                Date fecha = new Date();

                Hashtable h = new Hashtable();
                h.put("SALUDO", "Hola mundo");
                h.put("HORA", fecha);

                try {
                        // guardamos un objeto
                        archivos.guardarObjeto("cadena", lectura);
                        archivos.guardarObjeto("hashtable", h);

                        // recuperamos un objeto
                        String cadena = (String) archivos.recuperarObjeto("cadena");
                        Hashtable h2 = (Hashtable) archivos.recuperarObjeto("hashtable");

                        System.out.println("Contenido de Serial cadena: " + cadena);
                        System.out.println("Conteido de Serial hashtable: " + (String) h2.get("SALUDO"));
                } catch(IOException ex) {
                        System.err.println("Error de Obj.\n" + ex.toString());
                } catch(ClassNotFoundException ex) {
                        System.err.println("Error de Obj (Class).\n" + ex.toString());
                }
         }
  }
*/
}// Final de la clase
