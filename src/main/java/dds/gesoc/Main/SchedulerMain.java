package dds.gesoc.Main;

import dds.gesoc.model.repositorios.RepoEgresos;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerMain {

    final static Logger logger = LoggerFactory.getLogger(SchedulerMain.class);

    public static void main(String[] args) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = newJob(JObValidar.class).build();

        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(repeatSecondlyForever(5))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }


public static class JObValidar implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            RepoEgresos.getInstance().validarEgresos();
            logger.info("Validar egreso ejecutado");
        }
    }
}


//public class Main {
    
  //  private static RepoEgresos repo;

    /*public static void validar() {
        repo.validarEgresos();
        //System.out.println("Hola");
    }*/
    
//    public static void main(String[] args) {
//
//
//        repo = RepoEgresos.getInstance();
//
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(Main::validar, 0, 10, TimeUnit.DAYS);
//    }
//}
