//===================================================================
//IR �۽� ����̹�
//===================================================================

#include <linux/config.h>
#include <linux/module.h>
#include <linux/errno.h>
#include <linux/signal.h>
#include <linux/tty.h>
#include <linux/tty_flip.h>
#include <linux/major.h>
#include <linux/string.h>
#include <linux/fcntl.h>
#include <linux/ptrace.h>
#include <linux/ioport.h>
#include <linux/mm.h> 
#include <linux/init.h>
#include <linux/circ_buf.h>
#include <linux/serial.h>
#include <linux/console.h>
#include <linux/sysrq.h>
#include <linux/delay.h>
#include <linux/interrupt.h>
#include <linux/miscdevice.h>

#include <asm/system.h>
#include <asm/irq.h>
#include <asm/uaccess.h>
#include <asm/bitops.h>
#include <asm/delay.h>

#include <asm/arch-s3c2413/regs-gpio.h>
#include <asm/arch-s3c2413/regs-timer.h>
#include <asm/arch-s3c2413/map.h>


#define JIVE_IRTX_VERSION "1.0.0"
#define JIVE_IRTX_MODULE_NAME "irtx"
#define JIVE_IRTX_MISCDEV_MINOR MISC_DYNAMIC_MINOR
#define JIVE_IRTX_NAME   JIVE_MGMT_MODULE_NAME " hardware driver " JIVE_IRTX_VERSION
#define PFX JIVE_MGMT_MODULE_NAME ": "


//===================================================================
#define CarrierNothing	0
#define CarrierProcess	1

//===================================================================
int DisableCarrierNothing(void);
int EnableCarrierProcess(void);
int ir_delay(unsigned int count);

//===================================================================
static int IRTransmission_open (struct inode *inode, struct file *filp)
{	
   	return 0;
}

static int IRTransmission_release (struct inode *inode, struct file *filp)
{
   	return 0;
}

//===================================================================
static ssize_t IRTransmission_write (struct file *filp, const char *buf, size_t count, loff_t *f_pos)
{
	int MoveCount = CarrierNothing;
	int Triger,TxBitCnt,TxChkCnt;
	unsigned int TxData[100];
	
	MoveCount = count*4;
	copy_from_user(TxData, buf, MoveCount);
	TxBitCnt=0;
	TxChkCnt=count;

	DisableCarrierNothing();
	Triger = 0;

	cli();		// interrupt disable
	for(TxBitCnt=0; TxBitCnt<TxChkCnt; TxBitCnt++) {
		if (Triger) {
			DisableCarrierNothing();
			Triger = 0;
		}
		else {
			EnableCarrierProcess();
			Triger = 1;
		}
		ir_delay(TxData[TxBitCnt]);
	}
	//End bit
	EnableCarrierProcess();
	ir_delay(560);
	DisableCarrierNothing();

	sti();	// interrupt enable
	return 0;
}

//===================================================================
struct file_operations IRTransmission_fops = {
    	write:    	IRTransmission_write,
    	open:    	IRTransmission_open,
    	release:  	IRTransmission_release,
};

static struct miscdevice jive_irtx_miscdev = {
	JIVE_IRTX_MISCDEV_MINOR,
	JIVE_IRTX_MODULE_NAME,
	&IRTransmission_fops,
};

//===================================================================
static int __init IRTransmission_init(void)
{
	int result;

	result = misc_register (&jive_irtx_miscdev);
	if (result) {
		printk(KERN_WARNING "misc device register failed\n");
		return result;
	}
    	return 0;
}

static void __exit IRTransmission_exit(void)
{
	misc_deregister(&jive_irtx_miscdev);
}

//===================================================================
int DisableCarrierNothing()
{
	(*(volatile unsigned int __force *)S3C2413_TCON &= 0xFFFFF0FF);		
	(*(volatile unsigned int __force *)S3C2413_GPBCON &= 0xFFFFFFFF);  	// GPB1 :
	(*(volatile unsigned int __force *)S3C2413_GPBCON |= 0x00000004);  	// GPB1 : Normal Port Setting
	(*(volatile unsigned int __force *)S3C2413_GPBDAT &= 0xFFFFFFFD);	// GPB1 Low
	return 0;
}				

int EnableCarrierProcess()
{
	(*(volatile unsigned int __force *)S3C2413_GPBCON &= 0xFFFFFFF3);  	// GPB1 : 
	(*(volatile unsigned int __force *)S3C2413_GPBCON |= 0x00000008);  	// GPB1 : TOUT1 Setting
	(*(volatile unsigned int __force *)S3C2413_TCFG0 &= 0x00000000);	// Prescale 0x00
	(*(volatile unsigned int __force *)S3C2413_TCFG1 &= 0xFFFFFF0F);	// PCLK devide 1/2
	(*(volatile unsigned int __force *)S3C2413_TCFG1 &= 0xFFFFFF1F);	// PCLK devide 1/4
	(*(volatile unsigned int __force *)S3C2413_TCNTB(1) = 0x0294);		// 37.8Khz
	(*(volatile unsigned int __force *)S3C2413_TCMPB(1) = 0x14A);		
	(*(volatile unsigned int __force *)S3C2413_TCON &= 0xFFFFF0FF);		
	(*(volatile unsigned int __force *)S3C2413_TCON |= 0x00000200);		// Manual update
	(*(volatile unsigned int __force *)S3C2413_TCON |= 0x00000900);		// Start timer1 with Auto reload
	(*(volatile unsigned int __force *)S3C2413_TCON &= ~(0x00000200));	// Manual update mode disable	
	return 0;
}

int ir_delay(unsigned int count)
{
	int i, k;
	int temp = 0;
	for (i=0; i<count; i++) {
		for (k=0; k<49; k++) {
			temp++;
		}
	}
	return 0;
}

//===================================================================
module_init(IRTransmission_init);
module_exit(IRTransmission_exit);

MODULE_LICENSE("GPL");


