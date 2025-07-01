package hello.core.sigleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {}

    public static void main(String[] args) {
        SingletonService service = SingletonService.getInstance();
    }
}
