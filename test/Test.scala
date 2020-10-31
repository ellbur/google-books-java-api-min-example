
import java.io.File
import java.io.FileInputStream
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.services.books.BooksScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import scala.jdk.CollectionConverters._
import com.google.api.services.books.Books
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import java.io.FileReader
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver

object Test extends App {
  {
    val homeDir = new File(System.getProperty("user.home"))
    val passwordsDir = new File(homeDir, ".passwords")
    if (!passwordsDir.exists) {
      throw new RuntimeException(s"$passwordsDir does not exist")
    }
    
    val clientSecretsJSONFile = new File(passwordsDir, "secret.json")
    assert(clientSecretsJSONFile.exists)
    
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val jsonFactory = JacksonFactory.getDefaultInstance()
    
    // https://developers.google.com/api-client-library/java/google-api-java-client/oauth2#installed_applications
    val clientSecrets = GoogleClientSecrets.load(jsonFactory, new FileReader(clientSecretsJSONFile))

    val flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, List(BooksScopes.BOOKS).asJava).build()
    
    val app = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver)

    val credential = app.authorize(null)
    
    val books = new Books.Builder(httpTransport, jsonFactory, credential).setApplicationName("myapp").build()
    
    val library = books.mylibrary
    
    library.bookshelves.volumes.list("7").execute().getItems.asScala foreach { v =>
      println(s" * ${v.getVolumeInfo.getTitle} ${v.getVolumeInfo.getContentVersion}")
    }
  }
}

