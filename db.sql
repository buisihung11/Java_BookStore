create database BookStoreDB
go
USE [BookStoreDB]
GO
/****** Object:  Table [dbo].[tbl_Book]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Book](
	[BookID] [varchar](50) NOT NULL,
	[Title] [nvarchar](50) NOT NULL,
	[Price] [float] NULL,
	[Author] [nvarchar](50) NULL,
	[ImportDate] [datetime] NULL,
	[Quantity] [int] NOT NULL,
	[Status] [bit] NOT NULL,
	[ImagePath] [varchar](250) NULL,
	[Description] [nvarchar](250) NULL,
	[CategoryID] [varchar](50) NULL,
 CONSTRAINT [PK_tbl_Book] PRIMARY KEY CLUSTERED 
(
	[BookID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Category]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Category](
	[CategoryID] [varchar](50) NOT NULL,
	[CategoryName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tbl_Category] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Discount]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Discount](
	[Username] [varchar](50) NULL,
	[DiscountCode] [varchar](50) NOT NULL,
	[DiscountValue] [float] NOT NULL,
	[IsUsed] [bit] NOT NULL,
	[CreatedDate] [datetime] NULL,
 CONSTRAINT [PK_tbl_Discount_1] PRIMARY KEY CLUSTERED 
(
	[DiscountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Order]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Order](
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[CreatedDate] [datetime] NULL,
	[Username] [varchar](50) NOT NULL,
	[Total] [float] NOT NULL,
	[DiscountCode] [varchar](50) NULL,
	[recipientPhone] [varchar](11) NOT NULL,
	[recipientAddress] [varchar](300) NOT NULL,
	[CashedType] [varchar](50) NULL,
	[PaymentOnlineId] [varchar](200) NULL,
 CONSTRAINT [PK_tbl_Order] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_OrerDetail]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_OrerDetail](
	[OrderID] [int] NOT NULL,
	[BookID] [varchar](50) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Amount] [float] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_User]    Script Date: 11/28/2019 9:07:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_User](
	[Username] [varchar](50) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[FullName] [nvarchar](50) NOT NULL,
	[Address] [nvarchar](50) NULL,
	[Phone] [varchar](11) NOT NULL,
	[Status] [bit] NOT NULL,
	[IsAdmin] [bit] NOT NULL,
 CONSTRAINT [PK_tbl_User] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'NB1', N'New Book [Updated ]', 21.541000366210938, N'Hung Bui Update a', CAST(N'2019-11-28T00:00:00.000' AS DateTime), 25, 1, N'2.jpg', N'some thing about the book', N'FC2')
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'NB23', N'New Book 2', 2.2999999523162842, N'Hung Bui', CAST(N'2019-11-24T00:00:00.000' AS DateTime), 12, 1, N'2.jpg', N'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s,', N'AC1')
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'QWE', N'Head First C', 20.100000381469727, N'oRealli', CAST(N'2019-11-24T00:00:00.000' AS DateTime), 17, 1, N'3.jpg', N'book for C Beginner', N'FC2')
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'SH1', N'Sherlock Holme', 22.209999084472656, N'Arthur Conan Doyle[Updated]', CAST(N'2019-11-27T00:00:00.000' AS DateTime), 5, 0, N'3.jpg', N'Sherlock Holmes book[Updated]', N'FC2')
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'T51', N'The 5 Love Languages: The Secret to  [Updated 23]', 20.299999237060547, N'Gary Chapman', CAST(N'2019-11-24T00:00:00.000' AS DateTime), 0, 1, N'2.jpg', N'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris lacinia pharetra velit, sit amet congue lorem feugiat quis.', N'FC2')
INSERT [dbo].[tbl_Book] ([BookID], [Title], [Price], [Author], [ImportDate], [Quantity], [Status], [ImagePath], [Description], [CategoryID]) VALUES (N'WTC2', N'Where the Crawdads Sing [Updated]', 9.1000003814697266, N'Delia Owens', CAST(N'2019-11-24T00:00:00.000' AS DateTime), 85, 1, N'filename=2.jpg', N'Vivamus fermentum auctor risus ac ultricies. Aenean lacinia urna ante. ', N'FC2')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (N'AC1', N'Action')
INSERT [dbo].[tbl_Category] ([CategoryID], [CategoryName]) VALUES (N'FC2', N'Fiction')
INSERT [dbo].[tbl_Discount] ([Username], [DiscountCode], [DiscountValue], [IsUsed], [CreatedDate]) VALUES (N'user1', N'DC1', 0.3, 1, NULL)
INSERT [dbo].[tbl_Discount] ([Username], [DiscountCode], [DiscountValue], [IsUsed], [CreatedDate]) VALUES (N'user1', N'DC12', 0.20000000298023224, 1, CAST(N'2019-11-28T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_Discount] ([Username], [DiscountCode], [DiscountValue], [IsUsed], [CreatedDate]) VALUES (N'user1', N'DC2', 0.1, 1, NULL)
INSERT [dbo].[tbl_Discount] ([Username], [DiscountCode], [DiscountValue], [IsUsed], [CreatedDate]) VALUES (NULL, N'DC3', 0.5, 1, NULL)
INSERT [dbo].[tbl_Discount] ([Username], [DiscountCode], [DiscountValue], [IsUsed], [CreatedDate]) VALUES (N'user1', N'DC4', 0.3, 1, NULL)
SET IDENTITY_INSERT [dbo].[tbl_Order] ON 

INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (5, CAST(N'2019-11-26T00:00:00.000' AS DateTime), N'user1', 31.767398834228516, N'DC1', N'0919191919', N'Quan 9', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (6, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 43.082000732421875, NULL, N'0818181811', N'Quan 7', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (7, CAST(N'2019-11-26T00:00:00.000' AS DateTime), N'user2', 39.741001129150391, NULL, N'0919191191', N'Quan 7', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (8, CAST(N'2019-11-26T00:00:00.000' AS DateTime), N'user1', 18.269998550415039, N'DC2', N'0172717127', N'Quan 12', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (9, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 21.541000366210938, NULL, N'0919191919', N'Quan 7', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (10, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 20.299999237060547, NULL, N'09191919191', N'Quan 7', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (11, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 40.400001525878906, NULL, N'09191919191', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (12, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user2', 4.5999999046325684, NULL, N'01111111212', N'Quan 5', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (13, CAST(N'2019-11-27T19:36:02.810' AS DateTime), N'user2', 311.30001831054688, NULL, N'0911818111', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (14, CAST(N'2019-11-27T21:11:04.250' AS DateTime), N'user2', 58.400001525878906, NULL, N'09191919119', N'Quan 7', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (15, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 29.200000762939453, NULL, N'0981818181', N'Quan 8', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (16, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 2.2999999523162842, NULL, N'0911919191', N'Quan 8', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (17, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 42.5, NULL, N'0987654321', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (18, CAST(N'2019-11-27T00:00:00.000' AS DateTime), N'user1', 29.75, N'DC4', N'0918181818', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (19, CAST(N'2019-11-28T00:00:00.000' AS DateTime), N'user1', 32.159999847412109, N'DC12', N'0919191911', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (20, CAST(N'2019-11-28T00:00:00.000' AS DateTime), N'user1', 20.100000381469727, NULL, N'0919191911', N'Quan 1', NULL, NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (21, CAST(N'2019-11-28T00:00:00.000' AS DateTime), N'user1', 4.5999999046325684, NULL, N'0818181818', N'Quan 1', N'Normal', NULL)
INSERT [dbo].[tbl_Order] ([OrderID], [CreatedDate], [Username], [Total], [DiscountCode], [recipientPhone], [recipientAddress], [CashedType], [PaymentOnlineId]) VALUES (22, CAST(N'2019-11-28T00:00:00.000' AS DateTime), N'user1', 49.300003051757812, NULL, N'0818181881', N'Quan 1', N'Online', N'ch_1FjbmdLdCXW0gyX9VBUMD2Jm')
SET IDENTITY_INSERT [dbo].[tbl_Order] OFF
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (5, N'NB23', 1, 2.2999999523162842)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (5, N'NB1', 2, 43.082000732421875)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (6, N'NB1', 2, 43.082000732421875)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (7, N'WTC2', 2, 18.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (7, N'NB1', 1, 21.541000366210938)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (8, N'T51', 1, 20.299999237060547)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (11, N'T51', 1, 20.299999237060547)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (11, N'QWE', 1, 20.100000381469727)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (12, N'NB23', 2, 4.5999999046325684)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (13, N'WTC2', 32, 291.20001220703125)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (13, N'QWE', 1, 20.100000381469727)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (18, N'NB23', 1, 2.2999999523162842)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (18, N'QWE', 2, 40.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (19, N'QWE', 2, 40.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (20, N'QWE', 1, 20.100000381469727)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (21, N'NB23', 2, 4.5999999046325684)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (22, N'WTC2', 1, 9.1000003814697266)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (22, N'QWE', 2, 40.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (9, N'NB1', 1, 21.541000366210938)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (10, N'T51', 1, 20.299999237060547)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (14, N'WTC2', 2, 18.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (14, N'QWE', 2, 40.200000762939453)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (15, N'WTC2', 1, 9.1000003814697266)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (15, N'QWE', 1, 20.100000381469727)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (16, N'NB23', 1, 2.2999999523162842)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (17, N'NB23', 1, 2.2999999523162842)
INSERT [dbo].[tbl_OrerDetail] ([OrderID], [BookID], [Quantity], [Amount]) VALUES (17, N'QWE', 2, 40.200000762939453)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'admin', N'admin', N'Hung Bui', N'Quan 9', N'09191919191', 1, 1)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'customer1', N'customer1', N'Kaity Ha', N'asdasd@gmail.com', N'0761616161', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user1', N'user1', N'Jonhy', N'Quan 10', N'0181818118', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user2', N'user2', N'Huong Le', N'Quan 2', N'0919191911', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user3', N'user3', N'Van Nguyen', N'Quan 2', N'0911231332', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user4', N'user4', N'Tran Tuan', N'Quan 7', N'0198171711', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user56', N'user56', N'Hung Jonka', N'Quan 6', N'0199191911', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user6', N'asdasd', N'Tran Hai', N'Quan 7', N'0198171711', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user67', N'asdasd', N'Tran Hai', N'Quan 7', N'0198171711', 1, 0)
INSERT [dbo].[tbl_User] ([Username], [Password], [FullName], [Address], [Phone], [Status], [IsAdmin]) VALUES (N'user89', N'user89', N'user8asdas ', N'Quan 12', N'0191919191', 1, 0)
ALTER TABLE [dbo].[tbl_Book]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Book_tbl_Category] FOREIGN KEY([CategoryID])
REFERENCES [dbo].[tbl_Category] ([CategoryID])
GO
ALTER TABLE [dbo].[tbl_Book] CHECK CONSTRAINT [FK_tbl_Book_tbl_Category]
GO
ALTER TABLE [dbo].[tbl_Discount]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Discount_tbl_User] FOREIGN KEY([Username])
REFERENCES [dbo].[tbl_User] ([Username])
GO
ALTER TABLE [dbo].[tbl_Discount] CHECK CONSTRAINT [FK_tbl_Discount_tbl_User]
GO
ALTER TABLE [dbo].[tbl_Order]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Order_tbl_Discount] FOREIGN KEY([DiscountCode])
REFERENCES [dbo].[tbl_Discount] ([DiscountCode])
GO
ALTER TABLE [dbo].[tbl_Order] CHECK CONSTRAINT [FK_tbl_Order_tbl_Discount]
GO
ALTER TABLE [dbo].[tbl_Order]  WITH CHECK ADD  CONSTRAINT [FK_tbl_Order_tbl_User] FOREIGN KEY([Username])
REFERENCES [dbo].[tbl_User] ([Username])
GO
ALTER TABLE [dbo].[tbl_Order] CHECK CONSTRAINT [FK_tbl_Order_tbl_User]
GO
ALTER TABLE [dbo].[tbl_OrerDetail]  WITH CHECK ADD  CONSTRAINT [FK_tbl_OrerDetail_tbl_Book] FOREIGN KEY([BookID])
REFERENCES [dbo].[tbl_Book] ([BookID])
GO
ALTER TABLE [dbo].[tbl_OrerDetail] CHECK CONSTRAINT [FK_tbl_OrerDetail_tbl_Book]
GO
ALTER TABLE [dbo].[tbl_OrerDetail]  WITH CHECK ADD  CONSTRAINT [FK_tbl_OrerDetail_tbl_Order] FOREIGN KEY([OrderID])
REFERENCES [dbo].[tbl_Order] ([OrderID])
GO
ALTER TABLE [dbo].[tbl_OrerDetail] CHECK CONSTRAINT [FK_tbl_OrerDetail_tbl_Order]
GO
