package comp5216.myrecovery;

import com.google.gson.Gson;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

/**
 *  public void submitBlob(View view) {

 new Thread() {

 public void run() {
 try

 {
 DataMgt.uploadPatient();

 } catch (
 Exception e)

 {
 e.printStackTrace();
 }

 }
 }.start();
 }
 }
 *
 *
 *
 *
 *
 */

public class DataMgt {

    public static final String storageConnectionString = "DefaultEndpointsProtocol=http;" +
            "AccountName=myrecoveryf;" +
            "AccountKey=XpOYCHL65ScX40hUOGave2ZYrI0EC7XqbenkqaunRs79SOB2ttic4YiVs920jDqm1zzgnaEw4wYqCtDq3UoLmA==";

    //public Patient thispatient;

    public static String convertToJson(Patient patient){
        Gson gson=new Gson();

        String patientJson = gson.toJson(patient);

        return patientJson;

    }

    public static Patient convertFromJson(String patientJson){

        Gson gson=new Gson();

        Patient patient = gson.fromJson(patientJson, Patient.class);

        return patient;
    }

    private static CloudBlobContainer getContainer(String id) throws Exception {
        // Retrieve storage account from connection-string.

        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(storageConnectionString);

        // Create the blob client.
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        // Get a reference to a container.
        // The container name must be lower case

        // the containername should be patient's name
        CloudBlobContainer container = blobClient.getContainerReference(id);

        return container;
    }

    public static void uploadPatient(Patient patient) throws Exception {

        CloudBlobContainer container = getContainer(patient.getId());

        container.createIfNotExists();

        String fileName = "patientRecord";

        CloudBlockBlob imageBlob = container.getBlockBlobReference(fileName);

        imageBlob.uploadText(convertToJson(patient));
    }

    public static Patient downloadPatient(String id)throws Exception {

        CloudBlobContainer container = getContainer(id);

        CloudBlockBlob blob = container.getBlockBlobReference("patientRecord");

        String blobText=blob.downloadText();

        return convertFromJson(blobText);
    }

    public static boolean checkExists(String id) throws Exception {
        CloudBlobContainer container = getContainer(id);
        return container.exists();
    }
}
