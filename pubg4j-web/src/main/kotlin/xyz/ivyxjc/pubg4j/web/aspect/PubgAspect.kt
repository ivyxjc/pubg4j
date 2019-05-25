package xyz.ivyxjc.pubg4j.web.aspect

import lombok.extern.slf4j.Slf4j
import org.apache.http.client.methods.HttpRequestBase
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author Ivyxjc
 * @since 4/25/2018
 */


/**
 * @author Ivyxjc
 * @since 4/24/2018
 */
@Slf4j
@Aspect
@Component
class HttpClientAspect {

    private var log = LoggerFactory.getLogger(HttpClientAspect::class.java)


    @Pointcut("execution(public * org.apache.http.impl.client.InternalHttpClient.execute(..))")
    fun httpClientExecute() {
    }

    @Pointcut("execution(public * xyz.ivyxjc.pubg4j.web.httpclient.GetApi.filterPlayerName(..))")
    fun filterPlayer() {
    }

    @Before("httpClientExecute()")
    @Throws(Throwable::class)
    fun doHttpExecuteBefore(joinPoint: JoinPoint) {
        val objects = joinPoint.args
        if (objects[0] is HttpRequestBase) {
            val httpRequestBase = objects[0] as HttpRequestBase
            log.debug("start httpClient.execute:{}", httpRequestBase.uri.path)
        }
    }

    @After("httpClientExecute()")
    @Throws(Throwable::class)
    fun doHttpExecuteAfter(joinPoint: JoinPoint) {
        val objects = joinPoint.args
        if (objects[0] is HttpRequestBase) {
            val httpRequestBase = objects[0] as HttpRequestBase
            log.debug("end httpClient.execute:{}", httpRequestBase.uri.path)
        }
    }

    @Around("httpClientExecute()")
    @Throws(Throwable::class)
    fun doHttpExecuteRound(pjp: ProceedingJoinPoint): Any {
        val t1 = System.currentTimeMillis()
        val res = pjp.proceed()
        val t2 = System.currentTimeMillis()
        val objects = pjp.args
        if (objects[0] is HttpRequestBase) {
            val httpRequestBase = objects[0] as HttpRequestBase
            log.debug("httpclient executes costs: {}ms, url:{}", t2 - t1, httpRequestBase.uri.query)
        }
        return res
    }

    @Around("filterPlayer()")
    @Throws(Throwable::class)
    fun doFilterPlayer(pjp: ProceedingJoinPoint): Any? {
        val res = pjp.proceed()
        log.info("PubgPlayer detail : ")
        log.info(res?.toString())
        return res
    }
}
