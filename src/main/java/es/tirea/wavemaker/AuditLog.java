package es.tirea.wavemaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuditLog{
    private static final Logger auditLogger = LoggerFactory.getLogger("seguridad");
    
    public static void trace(String serviceTypeUser, String file, String accessType, String authorized, String msg){
        auditLogger.trace("["+serviceTypeUser+"]["+file+"]["+accessType+"]["+authorized+"] "+msg);
    }
    public static void debug(String serviceTypeUser, String file, String accessType, String authorized, String msg){
        auditLogger.debug("["+serviceTypeUser+"]["+file+"]["+accessType+"]["+authorized+"] "+msg);
    }
    
    public static void warn(String serviceTypeUser, String file, String accessType, String authorized, String msg){
        auditLogger.warn("["+serviceTypeUser+"]["+file+"]["+accessType+"]["+authorized+"] "+msg);
    }
    
    public static void info(String serviceTypeUser, String file, String accessType, String authorized, String msg){
        auditLogger.info("["+serviceTypeUser+"]["+file+"]["+accessType+"]["+authorized+"] "+msg);
    }
    
    public static void error(String serviceTypeUser, String file, String accessType, String authorized, String msg){
        auditLogger.error("["+serviceTypeUser+"]["+file+"]["+accessType+"]["+authorized+"] "+msg);
    }
}
