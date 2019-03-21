package com.a4sc11production.krlassist.model;

public class Gangguan {
   private String line_name;
   private String short_desc;
   private String stasiun_affected;
   private String severity;

   public Gangguan(String line_name, String short_desc, String stasiun_affected, String severity) {
      this.line_name = line_name;
      this.short_desc = short_desc;
      this.stasiun_affected = stasiun_affected;
      this.severity = severity;
   }

   public String getLine_name() {
      return line_name;
   }

   public void setLine_name(String line_name) {
      this.line_name = line_name;
   }

   public String getShort_desc() {
      return short_desc;
   }

   public void setShort_desc(String short_desc) {
      this.short_desc = short_desc;
   }

   public String getStasiun_affected() {
      return stasiun_affected;
   }

   public void setStasiun_affected(String stasiun_affected) {
      this.stasiun_affected = stasiun_affected;
   }

   public String getSeverity() {
      return severity;
   }

   public void setSeverity(String severity) {
      this.severity = severity;
   }
}
