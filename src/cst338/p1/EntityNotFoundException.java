package cst338.p1;

public final class EntityNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public EntityNotFoundException(){
    this("The entity does not exist.");
  }
  public EntityNotFoundException(String message){
    this(message,null);
  }
  public EntityNotFoundException(Throwable cause){
    this("The entity does not exist",cause);
  }
  public EntityNotFoundException(String message,Throwable cause){
    super(message,cause);
  }

}
