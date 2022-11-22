import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class TablaFrecuencia {
    ArrayList<String> rangos = new ArrayList<String>();
    //private int[] xi = new int[5];
    private float[] xi = new float[5];
    private int[] ni = new int[5];
    private float[] fi = new float[5];
    ;
    private int[] Ni;
    private float[] Fi;
    private float[] densidad;
    private float media;
    private float Me;
    private float Mo;
    float varianza;
    float desEstandar;
    float desMediana;
    float coeVariacion;
    ArrayList<Float> datosMedia = new ArrayList<Float>();
    ArrayList<Float> datosMediana = new ArrayList<Float>();
    ArrayList<Float> datosModa = new ArrayList<Float>();
    ArrayList<Float> datosVarianza = new ArrayList<Float>();
    ArrayList<Float> datosDesEstandar = new ArrayList<Float>();
    ArrayList<Float> datosDesMediana = new ArrayList<Float>();
    ArrayList<Float> datosCoeVariacion = new ArrayList<Float>();
    private int nVeces;

    public TablaFrecuencia() {
        iniciar();
    }

    void iniciar() {
        for (int i = 0; i < 1000; i++) {
            inicializarVariables(5);
            generarTablasAleatorias();
        }
        ordenar();

        creacionTablas(datosMedia, "Media");
        creacionTablas(datosMediana, "Mediana");
        creacionTablas(datosModa, "Moda");
        creacionTablas(datosVarianza, "Varianza");
        creacionTablas(datosDesEstandar, "Desviacion Estandar");
        creacionTablas(datosDesMediana, "Desviacion Mediana");
        creacionTablas(datosCoeVariacion, "Coeficiente de Variacion");
    }

    void creacionTablas(ArrayList<Float> tabla, String titulo) {
        inicializarVariables(10);
        System.out.println(titulo);
        crearTablaFrecuencia(tabla);
        mostrarDatos();
        cargarDatos(nVeces, titulo);
        indicadoresTendencia();
    }

    void inicializarVariables(int m) {
        System.out.println("m = " + m);
        xi = new float[m];
        ni = new int[m];
        fi = new float[m];
        Ni = new int[m + 2];
        Fi = new float[m + 2];
        densidad = new float[m + 2];
        inicializar(m);
        rangos.clear();
    }

    void inicializar(int m) {
        for (int i = 0; i < m + 2; i++) {
            Ni[i] = 0;
            Fi[i] = 0;
            densidad[i] = 0;
        }
    }

    void generarTablasAleatorias() {
        float[] rango = {10, 20, 40, 60, 70, 100};
        float[] cantidades = {12, 16, 42, 25, 5};

        //int ran = rango[rango.length-1] - rango[0];
        ArrayList<Float> datosGenerados = new ArrayList<Float>();
        ArrayList<ArrayList<Float>> datosGeneradosRango = new ArrayList<ArrayList<Float>>(5);

        for (int i = 0; i < 5; i++) {
            datosGeneradosRango.add(datos(rango[i], rango[i + 1], cantidades[i]));
            Collections.sort(datosGeneradosRango.get(i));
            datosGenerados.addAll(datosGeneradosRango.get(i));
        }
        generarTabla(datosGenerados, datosGeneradosRango, rango, cantidades, 5);
    }

    //
    void generarTabla(ArrayList<Float> datosGenerados, ArrayList<ArrayList<Float>> datosGeneradosRango, float[] rango, float[] cantidades, int m) {

        Float max = Collections.max(datosGenerados);
        Float min = Collections.min(datosGenerados);
        int n = datosGenerados.size();


        float ci = 0;
        for (int i = 0; i < m; i++) {
            ni[i] = datosGeneradosRango.get(i).size();
            //xi[i] = (int) (rango[i] + rango[i+1]) / 2;
            xi[i] = (float) (rango[i] + rango[i + 1]) / 2;
            fi[i] = (float) ni[i] / 100;
            Ni[i + 1] = Ni[i] + ni[i];
            Fi[i + 1] = (float) Ni[i + 1] / 100;
            ci = (float) (rango[i + 1] - rango[i]);
            densidad[i + 1] = fi[i] / ci;
        }
        Ni = Arrays.copyOfRange(Ni, 1, Ni.length);
        Fi = Arrays.copyOfRange(Fi, 1, Fi.length);
        Ni = Arrays.copyOfRange(Ni, 0, Ni.length - 1);

        Fi = Arrays.copyOfRange(Fi, 0, Fi.length - 1);
        int posRango = 2;
        for (int i = 1; i < Fi.length - 1; i++) {
            if ((0.5f > Fi[i - 1]) && (0.5f < Fi[i])) {
                posRango = i;
                break;
            }
        }

        densidad = Arrays.copyOfRange(densidad, 0, densidad.length - 1);
        densidad = Arrays.copyOfRange(densidad, 1, densidad.length);

        media = (float) (sumatoria(datosGenerados)) / datosGenerados.size();
        ci = (float) (rango[posRango + 1] - rango[posRango]);
        Me = (float) (datosGenerados.get(n / 2) + datosGenerados.get((n / 2) + 1)) / 2;

        Mo = calcularModa(datosGenerados);
        varianza = calcularVarianza(datosGenerados, media);
        desEstandar = (float) Math.sqrt(varianza);
        desMediana = calcularDesviacionMediana(datosGenerados, Me);
        coeVariacion = (desEstandar / media) * 100;

        if (n == 100) {
            datosMedia.add(media);
            datosMediana.add(Me);
            datosModa.add(Mo);
            datosVarianza.add(varianza);
            datosDesEstandar.add(desEstandar);
            datosDesMediana.add(desMediana);
            datosCoeVariacion.add(coeVariacion);
        }
        nVeces = m;
    }

    public float calcularModa(ArrayList<Float> muestra){
        HashMap <Float,Float> map = new HashMap();

        float repetido = 0, numMax = -1,repetidoCon = 0;

        for (float i : muestra) {
            //System.out.println();
            if (map.containsKey(i)) {
                repetido =  map.get(i);
                map.put(i, ++repetido);
            } else{
                map.put(i, 1f);
            }
        }

        for (Map.Entry<Float,Float> e : map.entrySet()) {
            if (repetidoCon < e.getValue()) {
                repetidoCon = e.getValue();
                numMax = e.getKey();
            }
        }

        System.out.println("la moda en la población es  " +   numMax);
        return numMax;
    }   //fin getModa


    float calcularVarianza(ArrayList<Float> datos, float media){
        float varianza =0;
        for (int i = 0; i < datos.size(); i++) {
            varianza += (float)Math.pow(datos.get(i), 2) - (2 * datos.get(i) * media) + Math.pow(media, 2);
        }
        return varianza / datos.size();
    }
    float calcularDesviacionMediana(ArrayList<Float> datos, float Me){
        float diferencia = 0;
        for (int i = 0; i < datos.size(); i++) {
            diferencia +=  Math.abs(datos.get(i) - Me);
        }
        return diferencia / datos.size();
    }
    void ordenar(){
        Collections.sort(datosMedia);
        Collections.sort(datosMediana);
        Collections.sort(datosModa);
        Collections.sort(datosVarianza);
        Collections.sort(datosDesEstandar);
        Collections.sort(datosDesMediana);
        Collections.sort(datosCoeVariacion);
    }
    void mostrarDatos(){
        String texto ="{" +
                "xi=" + Arrays.toString(xi) +
                "\nni=" + Arrays.toString(ni) +
                "\nfi=" + Arrays.toString(fi) +
                "\nNi=" + Arrays.toString(Ni) +
                "\nFi=" + Arrays.toString(Fi) +
                "\ndensidad=" + Arrays.toString(densidad) +
                "\nMedia=" + media +
                "\nMediana=" + Me +
                "\nModa=" + Mo +
                '}';
        System.out.println(texto);
    }

    void crearTablaFrecuencia(ArrayList<Float> datos){
        if(datos.size() == 1001){
            datos.remove(datos.size()-1);
        }
        if(datos.size() == 1002){
            datos.remove(datos.size()-1);
            datos.remove(datos.size()-1);
        }
        //int ran = rango[rango.length-1] - rango[0];
        ArrayList<Float> datosGenerados = new ArrayList<Float>();
        ArrayList<ArrayList<Float>> datosGeneradosRango = new ArrayList<ArrayList<Float>>(5);

        int m = (int) (3.3*Math.log10(datos.size()) + 1);
        inicializarVariables(m);
        Float max = datos.get(datos.size()-1);
        Float min = datos.get(0);


        float[] rango = generarRango(max, min, m);
        float[] cantidades = new float[m];
        for (int i = 0; i < m; i++){
            rangos.add(String.format("%f - %f",rango[i], rango[i+1]));
            ArrayList<Float> aux = new ArrayList<Float>();
            do{
                if (validarIntervalo(rango[i], rango[i+1], datos.get(0))){
                    aux.add(datos.get(0));
                    datos.remove(0);
                }
            }while(datos.size() != 0 && validarIntervalo(rango[i], rango[i+1], datos.get(0)));


            cantidades[i] = aux.size();
            datosGeneradosRango.add(aux);
            datosGenerados.addAll(datosGeneradosRango.get(i));
        }

        generarTabla(datosGenerados, datosGeneradosRango, rango, cantidades, m);
    }
    boolean validarIntervalo(float li,float Ls,float valor){
        if (valor >= li && Ls >= valor){
            return true;
        }
        return false;
    }

    float[] generarRango(Float max, Float min, int m){
        float ci = (max - min) / m;
        float[] rangos = new float[m+1];
        float rango = min;
        System.out.println("rango max = " + max +" rango min= " + min);
        for (int i = 0; i < m+1; i++) {

            rangos[i] = rango;
            rango += ci;
        }
        //rangos[0] = (float) Math.floor(rangos[0]);
        System.out.println(rangos[0]);
        return rangos;
    }

    int posMax(float[] rangos){
        int pos = 0;
        for (int i = 1; i < rangos.length; i++) {
            if (rangos[pos] < rangos[i]){
                pos = i;
            }
        }
        return pos;
    }

    ArrayList datos(float rangoI, float rangoF, float cantidad){
        ArrayList<Float> aleatorios = new ArrayList();
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int i = 0; i < cantidad; i ++){
            int valor = tlr.nextInt((int)rangoI, (int)rangoF + 1);
            aleatorios.add((float)valor);
        }
        //System.out.println(aleatorios);
        return aleatorios;
    }
    float sumatoria(ArrayList<Float> datos){
        float sum = 0;
        for (int i = 0; i < datos.size(); i++){
            sum += datos.get(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "{" +
                "xi=" + Arrays.toString(xi) +
                "\nni=" + Arrays.toString(ni) +
                "\nfi=" + Arrays.toString(fi) +
                "\nNi=" + Arrays.toString(Ni) +
                "\nFi=" + Arrays.toString(Fi) +
                "\ndensidad=" + Arrays.toString(densidad) +
                '}';
    }
    void cargarDatos(int m, String titulo){
        Archivo Escritura = new Archivo();
        String texto = titulo +"Li-Ls;xi;ni;fi;Ni;Fi;Fi*\n";
        for (int i = 0; i < m; i++) {
            texto += "" + rangos.get(i) +";" + xi[i] +";" + ni[i] +";" + fi[i] +";" + Ni[i] +";"+ Fi[i] +";"+ densidad[i] +"\n";
        }
        Escritura.ImprimirEn("pruebaFinal.txt",texto);
    }
    void indicadoresTendencia(){
        Archivo Escritura = new Archivo();
        String texto = "Media = " + media +
                "\nMediana = " + Me +
                "\nModa = " + Mo +
                "\nVarianza = " + varianza +
                "\nDesviacion estandar = " + desEstandar +
                "\nDesviacion Mediana = " + desMediana +
                "\nCoeficiente de variacion = " + coeVariacion + "\n";
        Escritura.ImprimirEn("pruebaFinal.txt",texto);
    }
}
