zip -r db.zip db/
VERSION=$1
/usr/share/maven/3.3.9/bin/mvn deploy:deploy-file -DgroupId=gov.hud.lrs -DartifactId=LrsDb -Dversion=$VERSION -DgeneratePom=true -Dpackaging=zip -DrepositoryId=nexus -Durl=http://10.0.2.16/content/repositories/snapshots  -Dfile=db.zip -DuniqueVersion=false
