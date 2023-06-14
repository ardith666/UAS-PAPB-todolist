package ardith.mobile.uaspapb_todolist;

public class ModelTodoActivity {
    String time;
    String todo;

    public ModelTodoActivity(String time, String todo){
        this.time = time;
        this.todo = todo;
    }

    public void setTodo(String todo){
        this.todo = todo;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getTodo(){
        return todo;
    }
}
