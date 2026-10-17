/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject26;
import java.util.Scanner; 
import java.util.Random;
/**
 *
 * @author Test
 */
public class Mavenproject26 {
  static Random random=new Random();
      static  Scanner input=new Scanner(System.in);

      static double[] getProbabilities(int min, int max, int option) {

    int size = max - min + 1;
    double[] probabilities = new double[size];

    if (option == 1) {
        for (int i = 0; i < size; i++) {
            probabilities[i] = 1.0 / size;
        }
    } else {
        while (true) {
            double sum = 0;

            for (int i = 0; i < size; i++) {
                System.out.print("Enter probability for time "
                        + (min + i) + ": ");

                probabilities[i] = input.nextDouble();
                sum += probabilities[i];
            }

            if (sum==1) {
                break;
            }

            System.out.println("Error! Probabilities must sum = 1.");
        }
    }

    return probabilities;
}
      
      static void displayDistribution(int min, double[] probabilities) {

    System.out.println("\nTime\tProbability\tRandom Digits");

    int lower = 1;

    for (int i = 0; i < probabilities.length; i++) {

        int upper;

        if (i == probabilities.length - 1) {
            upper = 100;
        } 
    
        else {
            upper = lower + (int) Math.round(probabilities[i] * 100)-1 ;
        }

        System.out.printf("%d\t%.2f\t\t%02d-%02d%n",
                min + i, probabilities[i], lower, upper);

        lower = upper + 1;
    }
}
      
      static int getTimeFromDigit(int min, double[] probabilities, int digit) {

    int lower = 1;

    for (int i = 0; i < probabilities.length; i++) {

        int upper;

        if (i == probabilities.length - 1) {
            upper = 100;
        } else {
            upper = lower + (int) Math.round(probabilities[i] * 100) - 1;
        }

        if (digit >= lower && digit <= upper) {
            return min + i;
        }

        lower = upper + 1;
    }

    return min;
}
      
      

    public static void main(String[] args) {
       
        System.out.print("Enter number of customers: "); 
        int customers = input.nextInt(); 
        
        System.out.print("Enter minimum interarrival time: ");
        int minInterarrival = input.nextInt();
        System.out.print("Enter maximum interarrival time: "); 
        int maxInterarrival = input.nextInt();
        
        System.out.print("Enter minimum service time: "); 
        int minService = input.nextInt(); 
        System.out.print("Enter maximum service time: ");
        int maxService = input.nextInt();
        
        System.out.println("\nInterarrival Time Distribution");
        System.out.println("1. Equal Probability");
        System.out.println("2. Different Probabilities");
        System.out.print("Choose an option: ");
        int interarrivalOption = input.nextInt();
        
        System.out.println("\nService Time Distribution");
        System.out.println("1. Equal Probability"); 
        System.out.println("2. Different Probabilities"); 
        System.out.print("Choose an option: "); 
        int serviceOption = input.nextInt();
        
        double[] interarrivalProbabilities = getProbabilities(minInterarrival, maxInterarrival, interarrivalOption);
        double[] serviceProbabilities = getProbabilities(minService, maxService, serviceOption);
        
        System.out.println("\nInterarrival Distribution Table");
displayDistribution(minInterarrival, interarrivalProbabilities);

System.out.println("\nService Distribution Table");
displayDistribution(minService, serviceProbabilities);
        
System.out.println("\nGenerating times for all customers:");
int[] arrivalTimes = new int[customers];
int[] interarrivalTimes = new int[customers];
int[] BeginTimes = new int[customers];
int[] EndTimes = new int[customers];
int[] waitingTimes = new int[customers];
int[] timeInSystem = new int[customers];
int[] interarrivalRandomDigits = new int[customers];
int[] serviceRandomDigits = new int[customers];
int[] serviceTimes = new int[customers];

for (int i = 0; i < customers; i++) {

int interarrivalDigit = 0;
int interarrival = 0;

if (i > 0) {
    interarrivalDigit = random.nextInt(100) + 1;

    interarrival = getTimeFromDigit(
            minInterarrival,
            interarrivalProbabilities,
            interarrivalDigit
    );
}

interarrivalRandomDigits[i] = interarrivalDigit;
interarrivalTimes[i] = interarrival;

if (i == 0) {
    arrivalTimes[i] = 0;
} else {
    arrivalTimes[i] = arrivalTimes[i - 1] + interarrivalTimes[i];
}

    int serviceDigit = random.nextInt(100) + 1;
      serviceRandomDigits[i]=serviceDigit;

    int service = getTimeFromDigit(
            minService,
            serviceProbabilities,
            serviceDigit
    );
  serviceTimes[i]=service;
    
    System.out.println("Customer " + (i + 1));
    System.out.println("Interarrival Digit: " + interarrivalDigit);
    System.out.println("Interarrival Time: " + interarrival);
    System.out.println("Arrival Time: " + arrivalTimes[i]);
    System.out.println("Service Digit: " + serviceDigit);
    System.out.println("Service Time: " + service);
    if (i == 0) {
   BeginTimes[i] = arrivalTimes[i];
} else {
    BeginTimes[i] = Math.max(
            arrivalTimes[i],
            EndTimes[i - 1]
    );
}
    
EndTimes[i] = BeginTimes[i] + service;

waitingTimes[i] = BeginTimes[i] - arrivalTimes[i];

timeInSystem[i] = waitingTimes[i] + service;
System.out.println("Time in System: " + timeInSystem[i]);
 
System.out.println("Service Start: " + BeginTimes[i]);
System.out.println("Service End: " + EndTimes[i]);
System.out.println("Waiting Time: " + waitingTimes[i]);
    System.out.println();
}
System.out.println("\nSimulation Table");

System.out.printf(
        "%-10s %-12s %-12s %-12s %-12s %-12s %-12s %-12s %-12s %-12s%n",
        "Customer",
        "Inter RD",
        "Inter Time",
        "Arrival",
        "Service RD",
        "Service Time",
        "Begin",
        "End",
        "Waiting",
        "In System"
);

for (int i = 0; i < customers; i++) {

    System.out.printf(
            "%-10d %-12d %-12d %-12d %-12d %-12d %-12d %-12d %-12d %-12d%n",
            i + 1,
            interarrivalRandomDigits[i],
            interarrivalTimes[i],
            arrivalTimes[i],
            serviceRandomDigits[i],
            serviceTimes[i],
            BeginTimes[i],
            EndTimes[i],
            waitingTimes[i],
            timeInSystem[i]
    );
}
int totalWaiting = 0;
int totalService = 0;
int customersWhoWait = 0;
int totalIdleTime = 0;
int totalInterarrival = 0;
int totalWaitingForThoseWhoWait = 0;
int totalTimeInSystem = 0;

for (int i = 0; i < customers; i++) {
    totalWaiting += waitingTimes[i];
    totalService += serviceTimes[i];
    if (i>0){
    totalInterarrival += interarrivalTimes[i];
    }
    totalTimeInSystem += timeInSystem[i];

    if (waitingTimes[i] > 0) {
        customersWhoWait++;
        totalWaitingForThoseWhoWait += waitingTimes[i];
    }
}
totalIdleTime = arrivalTimes[0];

for (int i = 1; i < customers; i++) {
    if (arrivalTimes[i] > EndTimes[i - 1]) {
        totalIdleTime += arrivalTimes[i] - EndTimes[i - 1];
    }
}
double averageWaitingTime = (double) totalWaiting / customers;

double averageServiceTime = (double) totalService / customers;

double probabilityOfWaiting = (double) customersWhoWait / customers;

System.out.println("\nPerformance Measures");

System.out.println("1. Average Waiting Time: " + averageWaitingTime);
System.out.println("2. Average Service Time: " + averageServiceTime);
System.out.println("3. Probability of Waiting: " + probabilityOfWaiting);

double probabilityServerIdle = (double) totalIdleTime / EndTimes[customers - 1];

double averageTimeBetweenArrivals = (double) totalInterarrival / (customers-1);

double averageWaitingForThoseWhoWait = 0;

if (customersWhoWait > 0) {
    averageWaitingForThoseWhoWait = (double) totalWaitingForThoseWhoWait / customersWhoWait;
}

double averageTimeInSystem = (double) totalTimeInSystem / customers;

System.out.println("4. Probability Server Idle Time: " + probabilityServerIdle);
System.out.println("5. Average Time Between Arrivals: " + averageTimeBetweenArrivals);
System.out.println("6. Average Waiting Time for Customers Who Wait: " + averageWaitingForThoseWhoWait);
System.out.println("7. Average Time in System: " + averageTimeInSystem);

    }
}
