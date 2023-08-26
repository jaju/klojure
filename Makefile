publish:
	./gradlew -Pdev=false clean publish

dev:
	./gradlew -Pdev=true run
