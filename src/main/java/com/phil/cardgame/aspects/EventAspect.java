package com.phil.cardgame.aspects;

import com.phil.cardgame.model.Deck;
import com.phil.cardgame.repository.EventRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventAspect {
    @Autowired
    EventRepository eventRepository;

    @AfterReturning(value = "execution(* com.phil.cardgame.service.GameService.createGame())", returning = "gameId")
    public void logCreateGame(JoinPoint joinPoint, Long gameId){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,null);
    }
    @AfterReturning("execution(* com.phil.cardgame.service.GameService.addPlayerToGame(..)) && args(gameId, playerId)")
    public void logAddPlayer(JoinPoint joinPoint, long gameId, String playerId){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,playerId);
    }
    @AfterReturning("execution(* com.phil.cardgame.service.GameService.dealToPlayer(..)) && args(gameId, playerId, numberRequested)")
    public void logDealToPlayer(JoinPoint joinPoint, long gameId, String playerId, int numberRequested){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,playerId);
    }
    @AfterReturning("execution(* com.phil.cardgame.service.GameService.addDeckToGame(..)) && args(gameId)")
    public void logAddDeck(JoinPoint joinPoint, long gameId){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,null);
    }
    @AfterReturning("execution(* com.phil.cardgame.service.GameService.removePlayerFromGame(..)) && args(gameId, playerId)")
    public void logRemovePlayer(JoinPoint joinPoint, long gameId, String playerId){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,playerId);
    }
    @AfterReturning("execution(* com.phil.cardgame.service.GameService.deleteGame(..)) && args(gameId)")
    public void logDeleteGame(JoinPoint joinPoint, long gameId){
        String action = joinPoint.getSignature().getName();
        eventRepository.addEvent(action,gameId,null);
    }

//    @Around("execution(* com.phil.cardgame.service.GameService.createGame())")
//    public Long registerCreateGame(ProceedingJoinPoint joinPoint) throws Throwable{
//        String action = joinPoint.getSignature().toString();
//        Long gameId = (Long) joinPoint.proceed();
//        eventRepository.addEvent(action,gameId,null);
//        System.out.println("+++ 1 "+joinPoint.getSignature().getName());
//        return gameId;
//    }
//    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId, playerId)")
//    public Object registerPlayerAction(ProceedingJoinPoint joinPoint, long gameId, String playerId) throws Throwable{
//        String action = joinPoint.getSignature().toString();
//        eventRepository.addEvent(action,gameId,playerId);
//        System.out.println("+++ 2 "+joinPoint.getSignature().getName());
//        return joinPoint.proceed();
//    }
//    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId, deck)")
//    public Object registerDeckAction(ProceedingJoinPoint joinPoint, long gameId, Deck deck) throws Throwable{
//        String action = joinPoint.getSignature().toString();
//        eventRepository.addEvent(action,gameId,null);
//        System.out.println("+++ 3 "+joinPoint.getSignature().getName());
//        return joinPoint.proceed();
//    }
//    @Around("execution(* com.phil.cardgame.service.GameService.*(..)) && args(gameId)")
//    public Object registerGameAction(ProceedingJoinPoint joinPoint, long gameId) throws Throwable{
//        String action = joinPoint.getSignature().toString();
//        eventRepository.addEvent(action,gameId,null);
//        System.out.println("+++ 4 "+joinPoint.getSignature().getName());
//        return joinPoint.proceed();
//    }
}
