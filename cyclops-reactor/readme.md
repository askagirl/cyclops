# Project Reactor Integration

v8.0.0 of cyclops-reactor and above is built using v2.5.0-M4 of Project Reactor

## Get cyclops-reactor


* [![Maven Central : cyclops-reactor](https://maven-badges.herokuapp.com/maven-central/com.aol.cyclops/cyclops-reactor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.cyclops/cyclops-reactor)
* [Javadoc for Cyclops-Reactor](http://www.javadoc.io/doc/com.aol.cyclops/cyclops-reactor)


# cyclops-reactor features include

1. Native for comprehensions for Reactor types
2. Native Monad Tranformer for Flux. FluxT also has native for comprehensions
3. Monad wrapping via AnyM / AnyMValue / AnyMSeq
4. Compatible with cyclops-react pattern matching
5. Ability to use Reactor types inside cyclops-react monad transformers (as the wrapping type, requires conversion to act as the nested type).



Use Reactor.<type> to create wrapped Reactor Monads.


Supported Reactor Monads include

* Flux
* Mono


## Example for comprehensions with Flux

```java
import static com.aol.cyclops.reactor.Reactor.ForFlux;

Flux<Integer> result = each2(Flux.just(10,20),a->Flux.<Integer>just(a+10)
                                             ,(a,b)->a+b);
	
//Flux[30,50]
 ```

## Example for comprehensions with Mono

```java
import static com.aol.cyclops.reactor.Reactor.ForMono;

Mono<Integer> result = each2(Mono.just(10),a->Mono.<Integer>just(a+10)
                                          ,(a,b)->a+b);

//Mono[30]
 ```
 
## FluxT monad transformer
 
```java
import static com.aol.cyclops.reactor.Reactor.fluxT;

FluxTSeq<Integer> nested = fluxT(Flux.just(Flux.just(1,2,3),Flux.just(10,20,30)));
FluxTSeq<Integer> mapped = nested.map(i->i*3);

//mapped = [Flux[ReactiveSeq[3,6,9],ReactiveSeq[30,60,90]]
```
## MonoT monad transformer

```java
import static com.aol.cyclops.reactor.Reactor.monoT;

FutureWTSeq<Integer> nestedFuture = monoT(Flux.just(Mono.just(1),Mono.just(10)));
mapped = nested.map(i->i*3);

//mapped =  [Flux[FutureW[3],FutureW[30]]
```
 		
