package org.ohgiraffers.file;

public class FileDTO {
  private String originFileName; // 원본 파일 이름
  private String savedFileName; // 저장된 파일 이름 (같은 이름의 파일이 업로드 될 수도 있으므로)
  private String filePath; // 파일 경로
  private String fileDescription; // 파일 설명
  private String type;

  public FileDTO() {}

  public FileDTO(
      String originFileName, String savedFileName, String filePath, String fileDescription) {
    this.originFileName = originFileName;
    this.savedFileName = savedFileName;
    this.filePath = filePath;
    this.fileDescription = fileDescription;
  }

  public String getOriginFileName() {
    return originFileName;
  }

  public void setOriginFileName(String originFileName) {
    this.originFileName = originFileName;
  }

  public String getSavedFileName() {
    return savedFileName;
  }

  public void setSavedFileName(String savedFileName) {
    this.savedFileName = savedFileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileDescription() {
    return fileDescription;
  }

  public void setFileDescription(String fileDescription) {
    this.fileDescription = fileDescription;
  }
}
