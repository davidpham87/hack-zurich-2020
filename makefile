doc:
	clojure -X:codox

show-do:
	cd target/doc && python3 -m http.server
