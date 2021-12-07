# Snakefist Team Project Journal 
# Link to Video Demo of Apple Clone Store Application: Front Office Portal and Back Office Portal
https://drive.google.com/file/d/1dFSjrgyjdU6K3-sfZv212cx1XuiVJSuH/view?usp=sharing

## Architecture Diagram
<img width="428" alt="Screenshot 2021-12-06 235640" src="https://user-images.githubusercontent.com/65844160/144989063-5593c916-0b75-4d97-9215-9dc85ee0c97e.png">

## Functional and Technical Requirements

### Functional Requirements
* Front Office Portal:
  1. User Sign-up/Login: Users need to Sign-up or Login to start purchasing products.
  2. Products Browsing Page: allows Users to browse all the products that are available on the website.
  3. Products Detail Page: allows Users to see more details of a product, adjust the quantity, and add the product to cart.
  4. Payment Page: allows Users to make a payment online by asking for their name, card info, and shipping address. A Payment Receive with an Order Confirmation number will shown after the payment is processed successfully. 
  5. Order Review Page: allows Users to review their order and Request a Refund. 
* Back Office Portal:
  1. Contact us functionality: used for customers to reach out with support team about general inquiries.
  2. Schedule a Genius appointment: provide the date and time of your next appointment 
  3. Request a refund: inputs the information regarding a specific order to provide the users with a refund. 
* REST APIs

### Technical Requirements
* Software Stack & Tools
  1. Front Office Portal:
    * Front-end: EJS as Template Engine in Node.js, HTML, CSS, 
    * Backend:
  2. Back Office Portal:
    * Front-end: HTML, CSS
    * Backend: Spring Framework, MySQL
  3. Deployment to GKE

### * During our class demo we presented front portal and back office with complete functionality, Okta, RabbitMQ as well as MySQL integration. We were requested to emphasize on the GCP deployment (missing during in-class demo) which is included in our recording. 
<br></br>

# Snakefist Individual Team Member's Journal
## Individual Journal by Thi Tuong Vi Nguyen by Dec 6, 2021:
### Accomplished Tasks:
#### Front Office Portal
* Product Details Page (Backend, Frontend)
* User Sign-up/Login Page (Backend, Frontend)
* Product Browsing Page(Backend, Frontend)
* Shopping Cart Page(Backend, Frontend)
* Order Page (Frontend)
* RabbitMQ (Frontend)
<img width="959" alt="Screenshot 2021-12-06 234026" src="https://user-images.githubusercontent.com/65844160/144986955-39719cf6-00e8-4f4d-a4e8-e5f087e426e8.png">
<img width="960" alt="Screenshot 2021-12-06 234045" src="https://user-images.githubusercontent.com/65844160/144986962-e2b7a5ee-ee74-42b6-a5e5-6c9b58038da4.png">
### Discussion of accomplishment:
* https://github.com/nguyensjsu/fa21-172-snake-fist/commit/225ab47782920cb057b544dd1f9a61d12be2cd14
* https://github.com/nguyensjsu/fa21-172-snake-fist/commit/fa847b99aec2c926b5f0fdad988c46b30d864691
* https://github.com/nguyensjsu/fa21-172-snake-fist/commit/4df1e04253fcf06eece6f0c94ac409a1da9c7149
### Discussion of challenges and solutions:

### Discussion of accomplishment:

## Individual Journal by Malaak Khalil by Dec 6, 2021:
### Accomplished Tasks:
GCP Deployment 
Kong API Gateway
Management
in commits
### Discussion of accomplishment:
Successfully completed all assigneed taskes (delpoy to GCP, KongAPI Gateway, ect)
### Discussion of challenges and solutions:
Biggest challange was deploying to GCP since we waited till the end and deploying all things 

## Individual Journal by Miguel Gonzalez by Dec 6, 2021:

### Accomplished Tasks:
* Entire Back Office Portal:
  1. Contact us functionality
  2. Schedule a Genius appointment
  3. Request a refund
  * All these share  the same Spring  Application with a unique RestAPI and three different tables in MySQL for each functionality. 
* Okta implementation for extra credit
* Google Cloud deployment in Kubernetes and CloudSQL.
  1. Creating CloudSQL instance
  2. Deploying to Docker Hub
  3. Deploying to GKE
  4. Initializing DB in CloudSQL

Did demo presentation for back office and GCP Kubernetes cluster. 
Refined README 

### Discussion of accomplishments, challenges and solutions:
Although I began working on the task a bit later than I had wished, I am quite proud of the work I have been able to produce in this period of time. I faced issues connecting the docker containers, getting the APIs to work properly, and it was the first time I programmed an app with Okta SSO; but in the end was able to overcome all. Seeing everything work in the end after struggling with the docker and GKE deployment was a fantastic accomplishment.

<br></br>
<br></br>
![Image](https://github.com/nguyensjsu/fa21-172-snake-fist/blob/96136d18bfc28bf51c5aab9f547acfdaa34ef5b3/Screen%20Shot%202021-12-06%20at%2011.52.53%20PM.png)
