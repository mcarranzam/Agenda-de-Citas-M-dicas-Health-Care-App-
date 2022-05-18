//Programa para manejo de citas.
import java.util.*;
import java.io.*;

class Node<T> {
    public T key;
    public Node<T> next;
    public Node(T data){
        this.key = data;
        this.next = null;
    }
}

abstract class List<T>{
    public Node<T> head, tail;
    public List() {
        this.head = new Node<T>(null);
        this.tail = new Node<T>(null);
    }

    public abstract void PushFront(T key);
    public abstract T TopFront();
    public abstract void PopFront();
    public abstract void PushBack(T key);
    public abstract T TopBack();
    public abstract void PopBack();
    public abstract boolean Find(T key);
    public abstract void Erase(T key);
    public abstract boolean Empty();
    public abstract void AddBefore(Node<T> n, T key);
    public abstract void AddAfter(Node<T> n, T key);
    public abstract void addAfter(Node<T> n, T key);
    public abstract void addBefore(Node<T> n, T key);
}

class SinglyLinkedList<T> extends List {
    public SinglyLinkedList(){
        this.head.key = null;
        this.head.next = null;
        this.tail.key = null;
        this.tail.next = null;
    }

    @Override
    public void PushFront(Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        Node newNode = new Node(key);
        if(this.Empty()){ // If the list is empty we set the newNode as the head and tail of the list
            this.head.next = newNode;
            this.tail.next = newNode;
        }
        else { // Cause the list is not empty it should have a head
            newNode.next = this.head.next; // So we know that the actual Head will become the second on the list
            this.head.next = newNode; // And the newNode becomes the new Head
        }
    }

    @Override
    public Object TopFront() {
        if(this.Empty()){ // We verify if the list is empty
            return "List is Empty";
        }
        return this.head.next.key; // In case is not empty we return the key on the node pointed by the head
    }

    @Override
    public void PopFront() {
        if(this.Empty()){ // We verify if the list is empty
            System.out.println("It's not possible to remove because list is empty");return;
        }
        if(this.head.next == this.tail.next){ // If the head is the same tail that means we just have 1 element
            this.head.next = null; // Then if we are removing that element the list will be empty
            this.tail.next = null; // So both head and tail must point to null
        }else{ // If not we have more than 1 element
            this.head.next = this.head.next.next; // We just need to change the head to the next node
        }
    }

    @Override
    public void PushBack(Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        Node newNode = new Node(key);
        if(this.Empty()){ // If the list is empty we set the newNode
            this.head.next = newNode; // as the head
            this.tail.next = newNode; // and tail of the list
        }else{ // Cause the list is not empty it should have a tail
            this.tail.next.next = newNode; // The last tail must point to the newNode
            this.tail.next = newNode; // The newNode will be the tail now
        }
    }

    @Override
    public Object TopBack() {
        if(this.Empty()){ // We verify if the list is empty
            return "List is Empty";
        }
        return this.tail.next.key; // If not we get the key of the node pointed by the tail
    }

    @Override
    public void PopBack() {
        if(this.Empty()){ // We verify if the list is empty
            System.out.println("It's not possible to remove because list is empty");return;
        }
        if(this.head.next == this.tail.next){ // If there's only 1 item on the list
            this.head.next = null; // We point the head to null
            this.tail.next = null; // And the same with the tail
            return;
        } // If there's more than 1 item
        Node node = this.head.next;
        while(node.next.next!=null){ // We need to find the node behind to the tail
            node = node.next;
        }
        node.next = null; // Once we find it we need to point to null with that node
        this.tail.next = node; // And this node will be the new tail
    }

    @Override
    public boolean Find(Object key) {
        Node node = this.head.next;
        while(node!=null){ // While the node exists
            if(node.key.equals(key)){ // We verify if the key is the same
                return true; // If yes return true
            }
            node = node.next; // If not keep going.
        } // If the node is null it's because we got to the final
        return false; // of the list and did not find the key
    }

    @Override
    public void Erase(Object key) {
        Node node = this.head.next;
        if (this.head.next.key.equals(key)) {
            this.PopFront(); return; // If the key is at the head we just run a PopFront() function
        } // If is not the head, we need to see the list item by item
        while(!node.next.key.equals(key)) { // The key of the next node is the key we are looking for?
            node = node.next; // 1) No: keep looking
            if(node.next==null){ // If the next node is null we are at final and without the key
                System.out.println("Key is not in the list");return;
            }
        } // 2) Yes: you found the key
        if (node.next.equals(tail.next) & this.tail.next.key.equals(key)) { // If the node is pointing to the tail
            this.PopBack();return; // and the key is in the tail we just run a PopBack() function
        } // If not, erase the item by changing the pointer of the node before the item to the node after it
        node.next = node.next.next;
    }

    @Override
    public boolean Empty() {
        return this.head.next == null; // If head is null list is empty
    }

    @Override
    public void AddBefore(Node node, Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        if(this.Empty()){ // If the list is empty we add the value.
            System.out.println("Node "+node+" is not in list, because it's empty so we are adding "+key+" to the list");
            this.PushBack(key);return;
        }
        if(this.head.next.equals(node)){ // If the node is the head we just run a PushFront() function
            this.PushFront(key);return;
        }
        Node node2 = this.head.next;
        while(node2.next!=null){ // We need to see the list item by item and we are verifying 1 node after
            if(!node2.next.equals(node)){ // to keep the node before the one we are looking for
                node2 = node2.next; //If is not the node we want keep going
            }else{ // If is the node
                Node newNode = new Node(key); // Create a new node with the new key
                newNode.next = node; // newNode has to point to the node we were looking for
                node2.next = newNode; // The node before the one we were searching needs to point to the new one
                return;
            }
        }
        System.out.println("Node is not in the list");
    }
  
    @Override
    public void addBefore(Node node, Object key) {
    }

    @Override
    public void addAfter(Node node, Object key) {
    }

    @Override
    public void AddAfter(Node node, Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        if(this.Empty()){ // If the list is empty we add the value.
            System.out.println("Node "+node+" is not in list, because it's empty so we are adding "+key+" to the list");
            this.PushBack(key);return;
        }
        Node node2 = this.head.next;
        while(node2!=null){ // While the node exist
            if(!node2.equals(node)){ // If is different for the node we are searching
                node2 = node2.next; // Keep going
            }else{ // Bingo! you have the node
                if(node.equals(this.tail.next)){
                    this.PushBack(key);return; // If the node is the tail just run a PushBack() function
                }
                Node newNode = new Node(key); // Create new node
                newNode.next = node2.next; // newNode points to the node next to the node we were searching
                node2.next = newNode; // The node we were looking for points to the newNode
                return;
            }
        }
        System.out.println("Node is not in the list");
    }

    public Object Data(int position) {
      Node node = this.head.next;
      int count = 0;
      if (count != position) {
          count++;
          node = node.next;
      } 
      else {
        //Object data = node.key;
        return node.key;
      }
      return node.key;
    }
      
    public int size(){ // Counts the number of items in the list
        Node node = this.head.next;
        int count = 0;
        while(node!=null){
            count++;
            node = node.next;
        }
        return count;
    }
    public boolean nullKey(Object key){
        if(key==null){ // Customer is trying to add a null value to the list
            System.out.println("It's not possible to add a null value");
            return true;
        }
        return false;
    }
  
    public void print(int count, int cases){
        Node printNode = this.head.next;
        System.out.print("[");
        for(int j=0;j<this.size();j++){
            if(j!= this.size()-1){
                System.out.print(printNode.key+",");
            }else{
                if(count==cases-1){
                    System.out.print(printNode.key);
                }else{
                    System.out.print(printNode.key+"]");
                }
            }
            printNode = printNode.next;
        }
    }
}

public class Main {
	
	//Variables para hacer referencia a la informacion persona
	public static String NOMBRE;
	public static String TELEFONO;
	public static String CORREO;

	//Variables hacer referencia a informacion de la cita
	public static String DIRECCION;
	public static String FECHA;
	public static String HORA;
	public static String DESCRIPCION;
	
	//Variable de la ruta_ del archivo.
	static File ArchivoTXT = new File ("Database.txt");

  //Se crea la Lista
  static SinglyLinkedList<String> cita = new SinglyLinkedList<String>();
  static SinglyLinkedList<String> prueba = new SinglyLinkedList<String>();
  static SinglyLinkedList<String> prueba1 = new SinglyLinkedList<String>();
  
	public static void main(String[] args){
		comprobarBD();
		menuOpcionesPrincipal();
	}

	//Menu de opciones.
	public static void menuOpcionesPrincipal() {
		Scanner opmenu = new Scanner(System.in);
		int opcion;
		
		do{	
		System.out.println("--------------------------------------------------------");
		System.out.println("                   AGENDA DE CITAS");
		System.out.println("--------------------------------------------------------\n");
			
		System.out.println("INGRESE UNA OPCION\n");
		System.out.println("1.- AGREGAR NUEVA CITA");
		System.out.println("2.- BUSCAR CITA ");
		System.out.println("3.- ACTUALIZAR CITA");
		System.out.println("4.- ELIMINAR CITA");
		System.out.println("5.- MOSTRAR TODAS LAS CITAS");
    System.out.println("6.- COMPROBAR TIEMPO DE EJECUCIÓN");
		System.out.println("7.- SALIR DEL PROGRAMA");
		opcion = opmenu.nextInt();
		System.out.println("--------------------------------------------------------\n");
		switch(opcion){
		case 1:
			//Opcion para agregar registros al archivo.
			System.out.println("--------------------------------------------------------");
			System.out.println("              Agregar Nuevo Registro");
			System.out.println("--------------------------------------------------------\n");
			agregarRegistro();
			System.out.println("--------------------------------------------------------\n");
			break;
		case 2:
			System.out.println("\n--------------------------------------------------------");
			System.out.println("                       Buscando...");
			System.out.println("--------------------------------------------------------\n");
			menuBuscarRegistro();
			System.out.println("\n--------------------------------------------------------");
			System.out.println("                    Fin de la busqueda. ");
			System.out.println("--------------------------------------------------------\n\n");
			break;
		case 3:
			System.out.println("Actualizar Datos");
			menuModificarRegistro();
			break;
		case 4:			
			System.out.println("Eliminar datos");
			menuEliminarRegistro();
			break;
		case 5:
			//Opcion para mostrar las citas agregadas.
			System.out.println("--------------------------------------------------------");
			System.out.println("        Total de registros dentro delarchivo");
			System.out.println("--------------------------------------------------------\n\n");
			mostrarRegistros();
			System.out.println("\n--------------------------------------------------------");
			System.out.println("    Fin del Total de registros dentro delarchivo ");
      System.out.println("--------------------------------------------------------\n");
			break;
    case 6:
        menuComprobarEjecucion();
        break;
    case 7:
			System.out.println("Salir");
			break;
		default:
			System.out.println("Opcion invalida");
			break;
		}
	}
  while(opcion !=7);
		
	}
	
	//Comprobar si el archivo existe.
	public static void comprobarBD(){
		try{
			if(ArchivoTXT.exists()){
				System.out.println("Puede Trabajar en el Archivo.\n");
			}
      else{ArchivoTXT.createNewFile();
				System.out.println("Se ha Creado el Archivo, ya puedes Trabajar\n");
			}
		}
    catch(Exception e){
			System.out.println("Error" + e.getMessage());
			System.out.println("No se puede Trabajar en el Archivo.\n");
		}
	}
	
	//Agregar Registro.
	public static void agregarRegistro(){
		Scanner scCampo = new Scanner (System.in);
		String SepCampo = "|";//Separador de campo
		int tamRegistro = 0;//Variable apra calcular tamaño del registro	
		
		try{
			
		BufferedWriter Fescribe=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ArchivoTXT,true), "utf-8"));	
			
		System.out.println("Ingrese el Nombre completo:");
		NOMBRE = scCampo.nextLine();
		
		System.out.println("\nIngrese el Teléfono:");
		TELEFONO = scCampo.nextLine();
		
		System.out.println("\nIngrese el Correo Electrónico:");
		CORREO = scCampo.nextLine();
		
		System.out.println("\nIngrese la Dirección de la Cita:");
		DIRECCION = scCampo.nextLine();
		
		System.out.println("\nIngrese la Fecha de la Cita:");
		FECHA= scCampo.nextLine();
		
		System.out.println("\nIngrese la Hora de la Cita:");
		HORA = scCampo.nextLine();
		
		System.out.println("\nIngrese una Descripción acerca de la Cita:");
		DESCRIPCION = scCampo.nextLine();
    
		tamRegistro = NOMBRE.length()+SepCampo.length()+TELEFONO.length()+SepCampo.length()+CORREO.length()+SepCampo.length()+
		DIRECCION.length()+SepCampo.length()+FECHA.length()+SepCampo.length()+HORA.length()+SepCampo.length()+DESCRIPCION.length()+SepCampo.length();
		
		Fescribe.write(tamRegistro+SepCampo+NOMBRE+SepCampo+TELEFONO+SepCampo+CORREO+SepCampo+DIRECCION+SepCampo+FECHA+SepCampo+HORA+SepCampo+DESCRIPCION+SepCampo);
		Fescribe.close();
    String cita1 = NOMBRE+SepCampo+TELEFONO+SepCampo+CORREO+SepCampo+DIRECCION+SepCampo+FECHA+SepCampo+HORA+SepCampo+DESCRIPCION+SepCampo;
    cita.PushBack(cita1);
		
		System.out.println("\nEl registro se guardo correctamente.\n");
		}
    catch(Exception e){
			System.out.println(e.getMessage());
		}
}

	//Menu con opciones para buscar.
	public static void menuBuscarRegistro() {
		Scanner scopc = new Scanner(System.in);
		int opcion=0;
		
		System.out.println("Ingrese una opcion\n");
		System.out.println("1.- Búsqueda por Nombre");
		System.out.println("2.- Búsqueda por Teléfono");
		System.out.println("3.- Búsqueda por Correo");
		System.out.println("4.- Regresar al Menú Principal");
		opcion= scopc.nextInt();
		switch(opcion){
		case 1:
			buscarRegistroNombre();
			break;
		case 2:
			buscarRegistroTelefono();
			break;
		case 3:
			buscarRegistroCorreo();
			break;
		case 4:
			menuOpcionesPrincipal();
			break;
		default:
			System.out.println("Opcion invalida");
			break;
		}
	}
	
	//Buscar Registro por nombre.
	public static void buscarRegistroNombre(){
		Scanner sc = new Scanner (System.in);
	
		String buscar, leer;
		boolean encontrado = true;
		
		try{
			
			BufferedReader br = new BufferedReader (new FileReader(ArchivoTXT));	
			System.out.println("Ingrese el Nombre: ");
			
			buscar = sc.nextLine();
			
			while((leer = br.readLine()) != null){
					
				StringTokenizer st = new StringTokenizer(leer, "|");

				if(leer.contains(buscar)){
					
					String nombre = null;
					
					while (nombre != buscar && encontrado ){
						String tamregistro = st.nextToken().trim();
			            nombre =  st.nextToken().trim();
			            String telefono =  st.nextToken().trim();
			            String correo =  st.nextToken().trim();
			            
			            String direccion =  st.nextToken().trim();
			            String fecha =  st.nextToken().trim();
			            String hora =  st.nextToken().trim();
			            String descripcion =  st.nextToken().trim();
			            
			        if(nombre.equals(buscar)){
			            	
				            encontrado = false;
				            
				            System.out.println("\nEl resultado de la busqueda " +"\""+ buscar + "\""+ " si existe...\n");
				            
				            System.out.println("\nEl Nombre es:\t\t" + nombre);
				            System.out.println("El Teléfono es:\t\t" + telefono);
				            System.out.println("El Correo es:\t\t" + correo);
				            
				            System.out.println("La Dirección es:\t" + direccion);
				            System.out.println("La Fecha es:\t\t" + fecha);
				            System.out.println("La Hora es:\t\t\t" + hora);
				            System.out.println("La Descripción es:\t" + descripcion + "\n");
				            }
						}
				}
        else { 
          System.out.println("\nEl Resultado de la Búsqueda " +"\""+ buscar + "\""+ " no existe...");
        }
			}
			br.close();
      cita.Find(buscar);
			
		}catch(Exception e){
			System.out.println("Error" + e );
		}
	}

	//Buscar Registro por telefono.
	public static void buscarRegistroTelefono(){
		Scanner sc = new Scanner (System.in);
	
		String buscar, leer;
		boolean encontrado = true;
		
		try{
			
			BufferedReader br = new BufferedReader (new FileReader(ArchivoTXT));	
			System.out.println("Ingrese el Teléfono: ");
			buscar = sc.nextLine();
			
			while((leer = br.readLine()) != null){
					
				StringTokenizer st = new StringTokenizer(leer, "|");

				
				if(leer.contains(buscar)){
					
					String telefono = null;
					
					while (NOMBRE != buscar && encontrado ){
						String tamregistro = st.nextToken().trim();
			            String nombre =  st.nextToken().trim();
			            telefono =  st.nextToken().trim();
			            String correo =  st.nextToken().trim();
			            
			            String direccion =  st.nextToken().trim();
			            String fecha =  st.nextToken().trim();
			            String hora =  st.nextToken().trim();
			            String descripcion =  st.nextToken().trim();
			            
			        if(telefono.equals(buscar)){
			            	
				            encontrado = false;
				            	
				            System.out.println("\nEl Resultado de la Búsqueda " +"\""+ buscar + "\""+ " si existe...\n");
				            
				            System.out.println("\nEl Nombre es:\t\t" + nombre);
				            System.out.println("El Teléfono es:\t\t" + telefono);
				            System.out.println("El Correo es:\t\t" + correo);
				            
				            System.out.println("El Dirección es:\t" + direccion);
				            System.out.println("La Fecha es:\t\t" + fecha);
				            System.out.println("La Hora es:\t\t\t" + hora);
				            System.out.println("La Descripción es:\t" + descripcion + "\n");
				            }
						}
				}else{System.out.println("\nEl Registro no Existe");}
			}
			br.close();
      cita.Find(buscar);
			
		}catch(Exception e){
			System.out.println("Error" + e );
		}
	}

	//Buscar Registro por correo.
	public static void buscarRegistroCorreo(){
		Scanner sc = new Scanner (System.in);
	
		String buscar, leer;
		boolean encontrado = true;
		
		try{
			BufferedReader br = new BufferedReader (new FileReader(ArchivoTXT));	
			System.out.println("Ingrese el Correo: ");
			buscar = sc.nextLine();
			
			while((leer = br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(leer, "|");
				if(leer.contains(buscar)){
					
					String correo = null;
					
					while (NOMBRE != buscar && encontrado ){
						String tamregistro = st.nextToken().trim();
			            String nombre =  st.nextToken().trim();
			            String telefono =  st.nextToken().trim();
			            correo =  st.nextToken().trim();
			            
			            String direccion =  st.nextToken().trim();
			            String fecha =  st.nextToken().trim();
			            String hora =  st.nextToken().trim();
			            String descripcion =  st.nextToken().trim();
			            
			        if(correo.equals(buscar)){
				            encontrado = false;
				            System.out.println("\nEl Resultado de la Búsqueda " +"\""+ buscar + "\""+ " si existe...\n");
				            
				            System.out.println("\nEl Nombre es:\t\t" + nombre);
				            System.out.println("El Teléfono es:\t\t" + telefono);
				            System.out.println("El Correo es:\t\t" + correo);
				            
				            System.out.println("El Dirección es:\t" + direccion);
				            System.out.println("La Fecha es:\t\t" + fecha);
				            System.out.println("La Hora es:\t\t\t" + hora);
				            System.out.println("La Descripción es:\t" + descripcion + "\n");
				            }
						}
				}
        else {
          System.out.println("\nEl Registro no Existe");
        }
			}
			br.close();
      cita.Find(buscar);
			
		}catch(Exception e){
			System.out.println("Error" + e );
		}
	}

	//Mostrar todos los registros que existen dentro del archivo.
	public static void mostrarRegistros(){
 		
		try{
			String linea = null;
			BufferedReader leerFichero = new BufferedReader (new FileReader(ArchivoTXT));
			
			while( (linea = leerFichero.readLine()) != null){

	            StringTokenizer mistokens = new StringTokenizer(linea, "|");//StringTokenizer sirve para separar el registro por campos.

	            while(mistokens.hasMoreTokens()){
  	            String tamregistro = mistokens.nextToken().trim();//Trimp sirve para eliminar espacios.
  	            NOMBRE =  mistokens.nextToken().trim();
  	            TELEFONO =  mistokens.nextToken().trim();
  	            CORREO =  mistokens.nextToken().trim();
  	            
  	            DIRECCION =  mistokens.nextToken().trim();
  	            FECHA =  mistokens.nextToken().trim();
  	            HORA =  mistokens.nextToken().trim();
  	            DESCRIPCION =  mistokens.nextToken().trim();
  
  	            System.out.println("El Nombre es:\t\t" + NOMBRE);
  	            System.out.println("El Teléfono es:\t\t" + TELEFONO);
  	            System.out.println("El Correo es:\t\t" + CORREO);
  	            
  	            System.out.println("La Dirección es:\t" + DIRECCION);
  	            System.out.println("La Fecha es:\t\t" + FECHA);
  	            System.out.println("La Hora es:\t\t\t" + HORA);
  	            System.out.println("La Descripción es:\t" + DESCRIPCION);
  	            
  	            System.out.println("\n");
  	   
  	            }
	            }
	         leerFichero.close();
		}
    catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//Menú de Opciones para Modificar
  public static void menuModificarRegistro() {
		Scanner scopc = new Scanner (System.in);
    int opcion = 0;

    System.out.println("Ingrese una Opción\n");
    System.out.println("1.- Modificar el número de Teléfono buscando por Telefono");
    System.out.println("2.- Modificar el Correo buscando por Correo");
    System.out.println("3.- Regresar al Menú Principal");
    opcion = scopc.nextInt();

    switch (opcion) {
      case 1:
        System.out.println("-------------------------------------------------------- ");
        System.out.println("1.- Modificar el número de Teléfono buscando por Telefono");
        System.out.println("----------------------------------------------- ---------");
        modificarRegistroTelefono();
        System.out.println("-------------------------------------------------------- ");
        System.out.println("               Modificación Finalizada.");
        System.out.println("-------------------------------------------------------- ");
        break;
      case 2:
        System.out.println("-------------------------------------------------------- ");
        System.out.println("2.- Modificar el Correo buscando por Correo\n");
        System.out.println("----------------------------------------------- ---------");
        modificarRegistroCorreo();
        System.out.println("-------------------------------------------------------- ");
        System.out.println("               Modificación Finalizada.");
        System.out.println("-------------------------------------------------------- ");
        break;
      case 3:
        menuOpcionesPrincipal();
        break;
      default:
        System.out.println("Opción Inválida");
        break;
    }
	}
  
  //Modificar Registro por Teléfono
	public static void modificarRegistroTelefono(){
		Scanner sc = new Scanner(System.in);
    String linea = null; //Almacenamiento de lectura del Archivo
    String buscar = null; //Codigo a borrar
    String SepCampo = "|";

    Vector lineasACopiar = new Vector();

    System.out.println("Ingrese el Teléfono que desea modificar:");
    buscar = sc.nextLine();

    try {
      FileReader fr = new FileReader(ArchivoTXT);
      BufferedReader br = new BufferedReader(fr);
      while (br.ready()) {
        linea = br.readLine();
        StringTokenizer mistokens = new StringTokenizer(linea, "|");
        if (linea.contains(buscar)) {
          while (mistokens.hasMoreTokens()) {
            String tamregistro = mistokens.nextToken().trim();
            String nombre = mistokens.nextToken().trim();
            String telefono = mistokens.nextToken().trim();
            String correo = mistokens.nextToken().trim();
            String direccion = mistokens.nextToken().trim();
            String fecha = mistokens.nextToken().trim();
            String hora = mistokens.nextToken().trim();
            String descripcion = mistokens.nextToken().trim();
            String registros = tamregistro + SepCampo + nombre + SepCampo + telefono + SepCampo + correo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;

            if (!telefono.equals(buscar)){
              lineasACopiar.add(registros);
            }
            else {
              System.out.println("--------------------------------------------------------");
              System.out.println("\n\t El Registro a Modificar es: ");
              System.out.println("\nEl Nombre es:\t\t" + nombre);
              System.out.println("El Teléfono es:\t\t" + telefono);
              System.out.println("El Correo es:\t\t" + correo);
              System.out.println("El Dirección es:\t" + direccion);
              System.out.println("La Fecha es:\t\t" + fecha);
              System.out.println("La Hora es:\t\t\t" + hora);
              System.out.println("La Descripción es:\t" + descripcion + "\n");
              System.out.println("-------------------------------------------------------- \n");
              System.out.println("\nIngrese el nuevo Teléfono");
              String newTelefono = sc.nextLine();
              String newRegistro = tamregistro + SepCampo + nombre + SepCampo + newTelefono + SepCampo + correo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;
              lineasACopiar.add(newRegistro);
            }
          }
        }
        else {
            System.out.println("\nEl Teléfono \"" + buscar + "\" no existe.");
        }
      }
      br.close();
      cita.Find(buscar);
      //cita.Erase(buscar);
      cita.PopBack();

      if (linea.contains(buscar)) {
        FileWriter fw = new FileWriter(ArchivoTXT);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < lineasACopiar.size(); i++) {
          linea = (String)lineasACopiar.elementAt(i);
          bw.write(linea);
        }
        bw.close();
        cita.PushBack(linea);
        System.out.println("\nEl Registro con número de Teléfono " + buscar + " ha sido modificado con éxito.");
      }     
    }
    catch (Exception e) {
      System.out.println("Error: " + e);
    }
	}

  //Modificar Registro por Correo
	public static void modificarRegistroCorreo(){
    Scanner sc = new Scanner(System.in);
    String linea = null; //Almacenamiento de lectura del Archivo
    String buscar = null; //Codigo a borrar
    String SepCampo = "|";

    Vector lineasACopiar = new Vector();

    System.out.println("Ingrese el Correo que desea modificar:");
    buscar = sc.nextLine();

    try {
      FileReader fr = new FileReader(ArchivoTXT);
      BufferedReader br = new BufferedReader(fr);
      while (br.ready()) {
        linea = br.readLine();
        StringTokenizer mistokens = new StringTokenizer(linea, "|");
        if (linea.contains(buscar)) {
          while (mistokens.hasMoreTokens()) {
            String tamregistro = mistokens.nextToken().trim();
            String nombre = mistokens.nextToken().trim();
            String telefono = mistokens.nextToken().trim();
            String correo = mistokens.nextToken().trim();
            String direccion = mistokens.nextToken().trim();
            String fecha = mistokens.nextToken().trim();
            String hora = mistokens.nextToken().trim();
            String descripcion = mistokens.nextToken().trim();
            String registros = tamregistro + SepCampo + nombre + SepCampo + telefono + SepCampo + correo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;

            if (!correo.equals(buscar)){
              lineasACopiar.add(registros);
            }
            else {
              System.out.println("--------------------------------------------------------");
              System.out.println("\n\t El Registro a Modificar es: ");
              System.out.println("\nEl Nombre es:\t\t" + nombre);
              System.out.println("El Teléfono es:\t\t" + telefono);
              System.out.println("El Correo es:\t\t" + correo);
              System.out.println("El Dirección es:\t" + direccion);
              System.out.println("La Fecha es:\t\t" + fecha);
              System.out.println("La Hora es:\t\t\t" + hora);
              System.out.println("La Descripción es:\t" + descripcion + "\n");
              System.out.println("-------------------------------------------------------- \n");
              System.out.println("\nIngrese el nuevo Correo");
              String newCorreo = sc.nextLine();
              int tamRegistroNew = nombre.length() + SepCampo.length() + telefono.length() + SepCampo.length() + correo.length() + SepCampo.length() + direccion.length() + SepCampo.length() + fecha.length() + SepCampo.length() + hora.length() + SepCampo.length() + descripcion.length();
              
              String newRegistro = tamRegistroNew + SepCampo + nombre + SepCampo + telefono + SepCampo + newCorreo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;
              lineasACopiar.add(newRegistro);
            }
          }
        }
        else {
            System.out.println("\nEl Correo \"" + buscar + "\" no existe.");
        }
      }
      br.close();
      cita.Find(buscar);
      //cita.Erase(buscar);
      cita.PopBack();

      if (linea.contains(buscar)) {
        FileWriter fw = new FileWriter(ArchivoTXT);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < lineasACopiar.size(); i++) {
          linea = (String)lineasACopiar.elementAt(i);
          bw.write(linea);
        }
        bw.close();
        cita.PushBack(linea);
        System.out.println("\nEl Registro con Correo " + buscar + " ha sido modificado con éxito.");
      }     
    }
    catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
  
	//Eliminar Registro del Archivo
	public static void menuEliminarRegistro() {
    Scanner scopc = new Scanner (System.in);
    int opcion = 0;

    System.out.println("Ingrese una Opción\n");
    System.out.println("1.- Eliminar Cita buscando por Teléfono");
    System.out.println("2.- Eliminar Cita buscando por Correo");
    System.out.println("3.- Regresar al Menú Principal");
    opcion = scopc.nextInt();

    switch (opcion) {
      case 1:
        eliminarRegistroTelefono();
        break;
      case 2:
        eliminarRegistroCorreo();
        break;
      case 3:
        menuOpcionesPrincipal();
        break;
      default:
        System.out.println("Opción Inválida");
        break;
    }
	}

	//Eliminar Registro por Teléfono
	public static void eliminarRegistroTelefono(){
	  Scanner sc = new Scanner(System.in);
    String linea = null; //Almacenamiento de lectura del Archivo
    String buscar = null; //Codigo a borrar
    String SepCampo = "|";

    Vector lineasACopiar = new Vector();

    System.out.println("Ingrese el Teléfono que desea Eliminar:");
    buscar = sc.nextLine();

    try {
      FileReader fr = new FileReader(ArchivoTXT);
      BufferedReader br = new BufferedReader(fr);
      while (br.ready()) {
        linea = br.readLine();
        StringTokenizer mistokens = new StringTokenizer(linea, "|");
        if (linea.contains(buscar)) {
          while (mistokens.hasMoreTokens()) {
            String tamregistro = mistokens.nextToken().trim();
            String nombre = mistokens.nextToken().trim();
            String telefono = mistokens.nextToken().trim();
            String correo = mistokens.nextToken().trim();
            String direccion = mistokens.nextToken().trim();
            String fecha = mistokens.nextToken().trim();
            String hora = mistokens.nextToken().trim();
            String descripcion = mistokens.nextToken().trim();
            String registros = tamregistro + SepCampo + nombre + SepCampo + telefono + SepCampo + correo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;

            if (!telefono.equals(buscar)){
              lineasACopiar.add(registros);
            }
            else {
              System.out.println("--------------------------------------------------------");
              System.out.println("\n\t El Registro a Eliminarr es: ");
              System.out.println("\nEl Nombre es:\t\t" + nombre);
              System.out.println("El Teléfono es:\t\t" + telefono);
              System.out.println("El Correo es:\t\t" + correo);
              System.out.println("El Dirección es:\t" + direccion);
              System.out.println("La Fecha es:\t\t" + fecha);
              System.out.println("La Hora es:\t\t\t" + hora);
              System.out.println("La Descripción es:\t" + descripcion + "\n");
            }
          }
        }
        else {
            System.out.println("\nEl Teléfono \"" + buscar + "\" no existe.");
        }
      }
      br.close();

      if (linea.contains(buscar)) {
        FileWriter fw = new FileWriter(ArchivoTXT);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < lineasACopiar.size(); i++) {
          linea = (String)lineasACopiar.elementAt(i);
          bw.write(linea);
        }
        bw.close();
        cita.Find(buscar);
        //cita.Erase(buscar);
        cita.PopBack();
        System.out.println("\nEl Registro con número de Teléfono " + buscar + " ha sido eliminado con éxito.");
      }     
    }
    catch (Exception e) {
      System.out.println("Error: " + e);
    }
	}

  //Eliminar Registro por Correo
	public static void eliminarRegistroCorreo(){
	  Scanner sc = new Scanner(System.in);
    String linea = null; //Almacenamiento de lectura del Archivo
    String buscar = null; //Codigo a borrar
    String SepCampo = "|";

    Vector lineasACopiar = new Vector();

    System.out.println("Ingrese el Correo que desea Eliminar:");
    buscar = sc.nextLine();

    try {
      FileReader fr = new FileReader(ArchivoTXT);
      BufferedReader br = new BufferedReader(fr);
      while (br.ready()) {
        linea = br.readLine();
        StringTokenizer mistokens = new StringTokenizer(linea, "|");
        if (linea.contains(buscar)) {
          while (mistokens.hasMoreTokens()) {
            String tamregistro = mistokens.nextToken().trim();
            String nombre = mistokens.nextToken().trim();
            String telefono = mistokens.nextToken().trim();
            String correo = mistokens.nextToken().trim();
            String direccion = mistokens.nextToken().trim();
            String fecha = mistokens.nextToken().trim();
            String hora = mistokens.nextToken().trim();
            String descripcion = mistokens.nextToken().trim();
            String registros = tamregistro + SepCampo + nombre + SepCampo + telefono + SepCampo + correo + SepCampo + direccion + SepCampo + fecha + SepCampo + hora + SepCampo + descripcion;

            if (!correo.equals(buscar)){
              lineasACopiar.add(registros);
            }
            else {
              System.out.println("--------------------------------------------------------");
              System.out.println("\n\t El Registro a Eliminarr es: ");
              System.out.println("\nEl Nombre es:\t\t" + nombre);
              System.out.println("El Teléfono es:\t\t" + telefono);
              System.out.println("El Correo es:\t\t" + correo);
              System.out.println("El Dirección es:\t" + direccion);
              System.out.println("La Fecha es:\t\t" + fecha);
              System.out.println("La Hora es:\t\t\t" + hora);
              System.out.println("La Descripción es:\t" + descripcion + "\n");
            }
          }
        }
        else {
            System.out.println("\nEl Correo \"" + buscar + "\" no existe.");
        }
      }
      br.close();

      if (linea.contains(buscar)) {
        FileWriter fw = new FileWriter(ArchivoTXT);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < lineasACopiar.size(); i++) {
          linea = (String)lineasACopiar.elementAt(i);
          bw.write(linea);
        }
        bw.close();
        cita.Find(buscar);
        //cita.Erase(buscar);
        cita.PopBack();
        System.out.println("\nEl Registro con Correo " + buscar + " ha sido eliminado con éxito.");
      }     
    }
    catch (Exception e) {
      System.out.println("Error: " + e);
    }
	}

  //Comprobar Ejecución
  public static void menuComprobarEjecucion() {
    Scanner scopc = new Scanner (System.in);
    int opcion = 0;

    System.out.println("Ingrese una Opción\n");
    System.out.println("1.- Comprobar Tiempo de Agregar Cita");
    System.out.println("2.- Comprobar Tiempo de Eliminar Cita");
    System.out.println("3.- Comprobar Tiempo de Buscar Cita");
    System.out.println("4.- Comprobar Tiempo de Modificar Cita");
    System.out.println("5.- Regresar al Menú Principal");
    opcion = scopc.nextInt();

    switch (opcion) {
      case 1:
        tiempoAgregarCita();
        break;
      case 2:
        tiempoEliminarCita();
        break;
      case 3:
        tiempoBuscarCita();
        break;
      case 4:
        tiempoModificarCita();
        break;
      case 5:
        menuOpcionesPrincipal();
        break;
      default:
        System.out.println("Opción Inválida");
        break;
    }
  }

  //Tiempo Agregar Cita
  public static void tiempoAgregarCita() {
    System.out.println("Ingrese el número de citas que desea Agregar:");
    Scanner sc = new Scanner (System.in);
    int n = sc.nextInt();
    int node = 0;
    int min = 96;
		int max = 123;
		Random random = new Random();
    long init=System.nanoTime();
		while(node < n) {
			int code=0;
			code = random.nextInt(max + min) + min;
			if(code < 123 && code > 96) {
				int d=Integer.valueOf(code);
				char c=(char)(d);
        String f =Character.toString(c);
        int num = (int)(Math.random()*1+5);
        int value = random.nextInt(3 + 1) + 1;
        switch (value) {
          case 1:
            prueba.PushBack(f);
            break;
          case 2:
            prueba.PushFront(f);
            break;
          case 3:
            int v = prueba.size()/2;
            Object j = prueba.Data(v);
            Node newNode = new Node(j);
            prueba.addBefore(newNode, f);
            break;
          case 4:
            int w = prueba.size()/2;
            Object k = prueba.Data(w);
            Node newNode1 = new Node(k);
            prueba.addBefore(newNode1, f);
            break;
          default:
            break;
        }
				node++;
			}
		}
    long end=System.nanoTime();
    int time= (int)((end-init));
    System.out.println("\nEl tiempo de Agregar " + n + " citas es de: " + time + " NanoSeconds\n");
    menuOpcionesPrincipal();
  }

  //Tiempo Eliminar Cita
  public static void tiempoEliminarCita() {
    System.out.println("Ingrese el número de citas que desea Eliminar:");
    Scanner sc = new Scanner (System.in);
    int n = sc.nextInt();
    int node = 0;
    int min = 96;
		int max = 123;
		Random random = new Random();
    
		while(node < n) {
			int code=0;
			code = random.nextInt(max + min) + min;
			if(code < 123 && code > 96) {
				int d=Integer.valueOf(code);
				char c=(char)(d);
        String f =Character.toString(c);
				prueba.PushFront(f);
				node++;
			}
		}
    int node1 = 0;
    long init=System.nanoTime();
    while(node1 < n) {
      prueba.PopBack();
      node1++;
		}
    long end=System.nanoTime();
    int time= (int)((end-init));
    System.out.println("\nEl tiempo de Eliminar " + n + " citas es de: " + time + " NanoSeconds\n");
    menuOpcionesPrincipal();
  }

  //Tiempo Buscar Cita
  public static void tiempoBuscarCita() {
    System.out.println("Ingrese el número de citas que desea Buscar:");
    Scanner sc = new Scanner (System.in);
    int n = sc.nextInt();
    int node = 0;
    int min = 96;
		int max = 123;
		Random random = new Random();
    
		while(node < n) {
			int code=0;
			code = random.nextInt(max + min) + min;
			if(code < 123 && code > 96) {
				int d=Integer.valueOf(code);
				char c=(char)(d);
        String f =Character.toString(c);
				prueba.PushFront(f);
				node++;
			}
		}
    int node1 = 0;
    long init=System.nanoTime();
    while(node1 < n) {
      String q = (String)prueba.TopFront();
      prueba.Find(q);
      prueba.PopFront();
      node1++;
		}
    long end=System.nanoTime();
    int time= (int)((end-init));
    System.out.println("\nEl tiempo de Buscar " + n + " citas es de: " + time + " NanoSeconds\n");
    menuOpcionesPrincipal();
  }

  //Tiempo Modificar Cita
  public static void tiempoModificarCita() {
    System.out.println("Ingrese el número de citas que desea Modificar:");
    Scanner sc = new Scanner (System.in);
    int n = sc.nextInt();
    int node = 0;
    int min = 96;
		int max = 123;
		Random random = new Random();
    
		while(node < n) {
			int code=0;
			code = random.nextInt(max + min) + min;
			if(code < 123 && code > 96) {
				int d=Integer.valueOf(code);
				char c=(char)(d);
        String f =Character.toString(c);
				prueba.PushFront(f);
				node++;
			}
		}
    prueba1 = prueba;
    int node1 = 0;
    long init=System.nanoTime();
    while(node1 < n) {
      String q = (String)prueba1.TopBack();
      prueba.PushFront(q);
      prueba.PopBack();
      prueba.PopBack();
      node1++;
		}
    long end=System.nanoTime();
    int time= (int)((end-init));
    System.out.println("\nEl tiempo de Modificar " + n + " citas es de: " + time + " NanoSeconds\n");
    menuOpcionesPrincipal();
  }
}