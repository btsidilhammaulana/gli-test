import java.util.Properties

fun loadProps(path: String): Properties {
  val props = Properties()
  val file = rootProject.file(path)
  if (file.canRead()) {
    props.load(file.inputStream())
  } else {
    throw GradleException("Could not read properties file on $path")
  }
  return props
}

extra["appProps"] = loadProps("config/app.properties")
extra["secretProps"] = loadProps("config/secret.properties")