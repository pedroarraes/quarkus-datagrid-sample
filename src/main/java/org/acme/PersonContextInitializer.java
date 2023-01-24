package org.acme;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = { Person.class }, schemaPackageName = "person_list")
interface PersonContextInitializer extends SerializationContextInitializer {

}
