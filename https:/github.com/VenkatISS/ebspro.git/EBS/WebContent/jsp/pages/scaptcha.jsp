<%-- <%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="image/jpeg" 
import="java.io.*, java.awt.*, java.awt.image.*,com.sun.image.codec.jpeg.*"%> --%>

<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="image/jpeg" 
import="java.io.*, java.awt.*, java.awt.image.*,javax.imageio.ImageIO"%>

<%
	Font f = new Font("Dialog",Font.BOLD,16);
	Color c = new Color(34,34,34);
    BufferedImage image = new BufferedImage(130, 30, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    g.setBackground(c);
    g.clearRect(0,0,130,30);
    g.setColor(Color.WHITE);
    g.setFont(f);
    g.drawString((request.getSession().getAttribute("captchastr")).toString(),25,21);
    g.getPaint();
    
    ImageIO.write(image, "jpeg", response.getOutputStream());
/*     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( response.getOutputStream());
    encoder.encode(image); */

%>