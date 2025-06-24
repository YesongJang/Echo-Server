package echo;

public class CacheProxyHandler extends ProxyHandler {

    // 모든 핸들러가 공유하는 캐시
    private static SafeTable<String, String> cache = new SafeTable<>();

    @Override
    protected String response(String msg) throws Exception {
        if (cache.containsKey(msg)) {
            System.out.println("cache hit for: " + msg);
            return cache.get(msg);
        }

        System.out.println("cache miss for: " + msg);
        String reply = super.response(msg);
        cache.put(msg, reply);
        return reply;
    }
}

