<h1>HuffmanCoding</h1>


<h2>Description</h2>
Java program that implements the Huffman Coding Algorithm using various helper files and data structures such as queues, circular linked lists, and Node classes and algorithms such as enqueue, dequeue, and peek. This program compresses data to a minimum size without losing any information by giving more frequently used characters a shorter corresponding code of bits. 
<br />


<h2>Languages and Skills Used</h2>

- <b>Java</b> 
- <b>Queue</b>
- <b>Enqueue/dequeue</b>

<h2>Environments Used </h2>

- <b>Windows 11</b> (21H2)

<h2>Program walk-through:</h2>

<p align="center">
Input file of data to be encoded/decoded: 
<br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/b5f30f3d-5ca5-4e86-a7a1-d45d6f2b0957" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
Creates sorted list of characters in input file and their frequencies: 
<br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/3ed5ca09-e365-4040-8567-c7530f642629" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
<br />
Creates tree structure out of the frequencies:  <br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/79dbf6ee-3469-424b-a7e0-e148d519ab3f" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
<br />
Produces corresponding bit encodings for each character: <br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/8f3393c7-726c-4429-a1d1-88f3ff96e8f0" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
<br />
Encodes data from input file into new file:  <br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/69b9d598-e7e6-4c8e-bccd-099fa964b6bb" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
<br />
Decode function is used to decode the encodings back to the original data:  <br/>
<img src="https://github.com/poojakmenon/HuffmanCoding/assets/145529639/2d7dc396-8395-445c-8bd4-51f4e17989ea" height="80%" width="80%" alt="Disk Sanitization Steps"/>
<br />
</p>

<!--
 ```diff
- text in red
+ text in green
! text in orange
# text in gray
@@ text in purple (and bold)@@
```
--!>
