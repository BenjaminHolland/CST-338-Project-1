package cst338.p1;

public class EntityDuplicateException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public EntityDuplicateException(){
    this("The entity already exists.");
  }
  public EntityDuplicateException(String message){
    this(message,null);
  }
  public EntityDuplicateException(Throwable cause){
    this("The entity already exists.",cause);
  }
  public EntityDuplicateException(String message,Throwable cause){
    super(message,cause);
  }
  

}
