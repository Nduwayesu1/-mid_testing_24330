package Dao;

import model1.Enum.ELocation_type;
import model1.HibernateUtil;
import model1.Location;
import model1.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.UUID;

public class LocationDao {


    public  String saveLocation(Location location){

        try{

            Session  session= HibernateUtil.getSessionFactory().openSession();
            Transaction tr=session.beginTransaction();
            session.persist(location);
            tr.commit();
            return "Data Saved Succesful";
        }catch(Exception ex){
            ex.printStackTrace();;
        }
        return null;
    }
    public String searchByVillages(String villageName) {
        String provinceName = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to find the province by village name
            String hql = "SELECT p.locationName FROM Location v JOIN v.parentLocation p " +
                    "WHERE v.locationName = :villageName AND v.type = :villageType";

            Query query = session.createQuery(hql, String.class);
            query.setParameter("villageName", villageName);
            query.setParameter("villageType", ELocation_type.VILLAGE);

            provinceName = (String) ((org.hibernate.query.Query<?>) query).uniqueResult();

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return provinceName;
    }


    public Location findByLocationCode(String locationCode) {
        Location location = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Location l WHERE l.locationCode = :locationCode";
            Query query = session.createQuery(hql, Location.class);
            query.setParameter("locationCode", locationCode);
            location = (Location) ((org.hibernate.query.Query<?>) query).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }



    public String getUserProvinceName(UUID userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);

            if (user != null) {
                Location location = user.getLocation();

                // Traverse up to find the location of type PROVINCE
                while (location != null && location.getType() != ELocation_type.PROVINCE) {
                    location = location.getParentLocation(); // Assuming getParentLocation() provides the parent
                }

                // If we find a location of type PROVINCE, return its name
                if (location != null && location.getType() == ELocation_type.PROVINCE) {
                    return location.getLocationName();
                }
            }

            return "Province not found for user or user not found.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while retrieving the province name.";
        }
    }

}





