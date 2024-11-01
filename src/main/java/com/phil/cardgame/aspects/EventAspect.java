package com.phil.cardgame.aspects;

import com.phil.cardgame.model.Deck;
import com.phil.cardgame.repository.EventRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventAspect {
    @Autowired
    EventRepository eventRepository;

    @Around("execution(* com.phil.cardgame.service.GameService.createGame())")
    public Long registerCreateGame(ProceedingJoinPoint joinPoint) throws Throwable{
        String action = joinPoint.getSignature().toString();
        Long gameId = (Long) joinPoint.proceed();
        eventRepository.addEvent(action,gameId,null);
        System.out.println(">>> got here");
        return gameId;
    }
    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId, playerId)")
    public Object registerPlayerAction(ProceedingJoinPoint joinPoint, long gameId, String playerId) throws Throwable{
        String action = joinPoint.getSignature().toString();
        eventRepository.addEvent(action,gameId,playerId);
        return joinPoint.proceed();
    }
    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId, deck)")
    public Object registerDeckAction(ProceedingJoinPoint joinPoint, long gameId, Deck deck) throws Throwable{
        String action = joinPoint.getSignature().toString();
        eventRepository.addEvent(action,gameId,null);
        return joinPoint.proceed();
    }
    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId)")
    public Object registerGameAction(ProceedingJoinPoint joinPoint, long gameId) throws Throwable{
        String action = joinPoint.getSignature().toString();
        eventRepository.addEvent(action,gameId,null);
        return joinPoint.proceed();
    }
}
