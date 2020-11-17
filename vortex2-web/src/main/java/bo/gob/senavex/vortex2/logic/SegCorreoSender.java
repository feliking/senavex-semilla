/**
 *   ____
 *  / ___|  ___ _ __   __ ___   _______  __
 *  \___ \ / _ \ '_ \ / _` \ \ / / _ \ \/ /
 *   ___) |  __/ | | | (_| |\ V /  __/>  <
 *  |____/ \___|_| |_|\__,_| \_/ \___/_/\_\
 *
 *  Copyright © 2020
 *  http://www.senavex.gob.bo/licenses/LICENSE-1.0
 */
package bo.gob.senavex.vortex2.logic;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.gob.senavex.vortex2.Model;
import bo.gob.senavex.vortex2.model.SegCorreo;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Session;
import static bo.gob.senavex.vortex2.logic.SegCorreoLogic.*;
import bo.gob.senavex.vortex2.model.SegOperador;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.hiska.result.Document;
import com.hiska.result.Param;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

@Singleton
@LocalBean
@lombok.Getter
public class SegCorreoSender {
   @PersistenceContext(unitName = Model.SEG_PERSIST)
   private EntityManager em;
   @Resource(lookup = Model.VORTEX2_MAIL)
   private Session sessionMail;

   public SegCorreoSender() {
   }

   public SegCorreoSender(EntityManager em) {
      this.em = em;
   }

   @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
   public void segCorreoSender() {
      LOGGER.log(Level.FINE, "===========SEND MAILS==============");
      List<SegCorreo> list = em.createQuery("SELECT o FROM SegCorreo o WHERE o.estado in (:p1, :p2)")
            .setParameter("p1", E_REGISTRADO)
            .setParameter("p2", E_RENVIADO)
            .setMaxResults(10)
            .getResultList();
      for (SegCorreo it : list) {
         segCorreoSender(it);
      }
   }

   public void segCorreoSender(SegCorreo it) {
      it.setEstado(E_ENVIADO);
      em.merge(it);
      String to = it.getCorreoDestino();
      String cc = it.getCorreoCopia();
      Document mail = null;
      Param plantilla = it.getPlantilla();
      if (P_ACTIVACION.equals(plantilla)) {
         mail = SegCorreoTemplate.usuarioActivarMail(it);
      } else if (P_RECUPERAR.equals(plantilla)) {
         mail = SegCorreoTemplate.usuarioRecuperacionMail(it);
      } else if (P_INVITACION.equals(plantilla)) {
         SegOperador op = em.find(SegOperador.class, it.getIdReferenciaLong());
         mail = SegCorreoTemplate.usuarioInvitacionMail(it, op);
      }
      if (mail != null) {
         enviarEmail(to, cc, mail.getFileName(), mail);
      } else {
         LOGGER.log(Level.WARNING, "No hay plantilla");
      }
   }

   private void enviarEmail(String toEmails, String ccEmails, String subject, Document... documents) {
      try {
         LOGGER.log(Level.INFO, "Email Props: {0} ", sessionMail.getProperties());
         LOGGER.log(Level.FINE, "Email to: {0} cc:{1} subject: {2}", new Object[]{toEmails, ccEmails, subject});
         MimeMessage message = new MimeMessage(sessionMail);
         String from = sessionMail.getProperty("mail.from");
         message.setFrom(new InternetAddress(from, "NoReply"));
         message.setSubject(subject);
         Multipart multipart = new MimeMultipart();
         for (int i = 0; i < documents.length; i++) {
            Document document = documents[i];
            boolean isPlain = document.isContentPlain();
            BodyPart bodyPart = new MimeBodyPart();
            if (i > 0) {
               bodyPart.setFileName(document.getFileName());
            }
            bodyPart.setDescription(document.getFileName());
            if (isPlain) {
               String content = new String(document.getContent());
               bodyPart.setContent(content, document.getContentMimeType() + "; charset=utf-8");
            } else {
               bodyPart.setContent(document.getContent(), document.getContentMimeType());
            }
            multipart.addBodyPart(bodyPart);
         }
         message.setContent(multipart);
         message.setSentDate(new Date());
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails, false));
         if (ccEmails != null) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmails, false));
         }
         Transport.send(message);
      } catch (Exception e) {
         LOGGER.log(Level.SEVERE, "Error al enviar el correo electonico", e);
      }
   }

   private static final Logger LOGGER = Logger.getLogger(SegCorreoSender.class.getName());
}
