package io.sharedstreets.data.outputs;

import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Encoder;
import io.sharedstreets.data.SharedStreetsReference;

import java.io.IOException;

public class SharedStreetsReferenceJSONEncoder implements Encoder {

    @Override
    public void encode(Object obj, JsonStream stream) throws IOException {
        if (obj.getClass().equals(SharedStreetsReference.class)) {
            SharedStreetsReference ssr = (SharedStreetsReference) obj;

            stream.writeObjectStart();

            stream.writeObjectField("id");
            stream.writeVal(ssr.id.toString());
            stream.writeMore();

            stream.writeObjectField("geometryId");
            stream.writeVal(ssr.geometry.id.toString());
            stream.writeMore();

            stream.writeObjectField("formOfWay");
            stream.writeVal(ssr.formOfWay.getValue());
            stream.writeMore();

            stream.writeObjectField("locationReferences");

            stream.writeArrayStart();


            for(int i = 0; i < ssr.locationReferences.length; i++) {
                stream.writeObjectStart();

                stream.writeObjectField("sequence");
                stream.writeVal(ssr.locationReferences[i].sequence);
                stream.writeMore();

                stream.writeObjectField("point");
                    stream.writeArrayStart();
                        stream.writeVal(ssr.locationReferences[i].point.getX());
                        stream.writeMore();
                        stream.writeVal(ssr.locationReferences[i].point.getY());
                    stream.writeArrayEnd();

                if(ssr.locationReferences[i].distanceToNextRef != null) {

                    stream.writeMore();
                    stream.writeObjectField("bearing");
                    stream.writeVal(Math.round(ssr.locationReferences[i].bearing*10.0)/10.0d);
                    stream.writeMore();

                    stream.writeObjectField("distanceToNextRef");
                    stream.writeVal(Math.round(ssr.locationReferences[i].distanceToNextRef*100.0)/100.0d); // ship centimeter precision

                }

                if(ssr.locationReferences[i].intersection != null) {
                    stream.writeMore();
                    stream.writeObjectField("intersectionId");
                    stream.writeVal(ssr.locationReferences[i].intersection.id.toString());

                }

                stream.writeObjectEnd();

                if(i + 1 < ssr.locationReferences.length)
                    stream.writeMore();
            }

            stream.writeArrayEnd();
            stream.writeObjectEnd();

        }
    }
}

