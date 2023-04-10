package mvc;

abstract public class Model extends Bean{
    private boolean unsavedChanges = false;
    public String fileName = null;

    public void changed(){
        firePropertyChange(null, null, null);
        unsavedChanges = true;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String name){
        this.fileName = name;
    }

    public void setUnsavedChanges(boolean b) {
        unsavedChanges = b;
    }

    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }
}
